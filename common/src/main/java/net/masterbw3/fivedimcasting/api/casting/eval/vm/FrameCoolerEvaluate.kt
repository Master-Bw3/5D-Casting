package net.masterbw3.fivedimcasting.api.casting.eval.vm

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.eval.CastResult
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM
import at.petrak.hexcasting.api.casting.eval.vm.ContinuationFrame
import at.petrak.hexcasting.api.casting.eval.vm.FrameEvaluate
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.utils.serializeToNBT
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import at.petrak.hexcasting.api.utils.getList
import net.masterbw3.fivedimcasting.api.utils.NBTBuilder

/**
 * A list of patterns to be evaluated in sequence.
 * @property list the *remaining* list of patterns to be evaluated
 * @property isMetacasting only for sound effects, if this is being cast from a hermes / iris
 */
data class FrameCoolerEvaluate(
        val list: SpellList, val isMetacasting: Boolean,
) : ContinuationFrame {
    // Discard this frame and keep discarding frames.
    override fun breakDownwards(stack: List<Iota>) = false to stack

    // Step the list of patterns, evaluating a single one.
    override fun evaluate(
            continuation: SpellContinuation,
            level: ServerWorld,
            harness: CastingVM
    ): CastResult {
        // If there are patterns left...
        return if (list.nonEmpty) {
            val newCont = if (list.cdr.nonEmpty) { // yay TCO
                // ...enqueue the evaluation of the rest of the patterns...
                continuation.pushFrame(FrameCoolerEvaluate(list.cdr, this.isMetacasting))
            } else continuation
            // ...before evaluating the first one in the list.
            val update = harness.executeInner(list.car, level, newCont)
            if (this.isMetacasting && update.sound != HexEvalSounds.MISHAP) {
                update.copy(sound = HexEvalSounds.HERMES)
            } else {
                update
            }
        } else {
            // If there are no patterns (e.g. empty Hermes), just return OK.
            CastResult(ListIota(list), continuation, null, listOf(), ResolvedPatternType.EVALUATED, HexEvalSounds.HERMES)
        }
    }

    override fun serializeToNBT() = NBTBuilder {
        "type" %= "evaluate"
        "patterns" %= list.serializeToNBT()
        "isMetacasting" %= isMetacasting

    }

    override fun size() = list.size()

    override val type: ContinuationFrame.Type<*> = FrameEvaluate.TYPE


    companion object {
        @JvmField
        val TYPE: ContinuationFrame.Type<FrameCoolerEvaluate> = object : ContinuationFrame.Type<FrameCoolerEvaluate> {
            override fun deserializeFromNBT(tag: NbtCompound, world: ServerWorld): FrameCoolerEvaluate {
                return FrameCoolerEvaluate(
                        HexIotaTypes.LIST.deserialize(
                                tag.getList("patterns", NbtElement.COMPOUND_TYPE),
                                world
                        )!!.list,
                        tag.getBoolean("isMetacasting")
                )
            }

        }
    }
}