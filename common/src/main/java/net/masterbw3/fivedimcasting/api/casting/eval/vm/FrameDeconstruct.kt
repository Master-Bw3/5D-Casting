package net.masterbw3.fivedimcasting.api.casting.eval.vm

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.eval.CastResult
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM
import at.petrak.hexcasting.api.casting.eval.vm.ContinuationFrame
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.utils.getList
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.masterbw3.fivedimcasting.api.casting.iota.StreamIota
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.server.world.ServerWorld

data class FrameDeconstruct(
        val genNextCode: SpellList,
        val maps: List<SpellList>,
) : ContinuationFrame {
    override val type: ContinuationFrame.Type<*>
        get() = FrameDeconstruct.TYPE

    override fun breakDownwards(stack: List<Iota>): Pair<Boolean, List<Iota>> {
        return true to stack
    }

    override fun evaluate(continuation: SpellContinuation, level: ServerWorld, harness: CastingVM): CastResult {
        val initialIota = harness.image.stack.last()
        val newInitialIota = harness.image.stack[harness.image.stack.size - 2]

        val stack = harness.image.stack.dropLast(2).toMutableList()

        val stream = StreamIota(newInitialIota, genNextCode.toMutableList(), maps)

        stack.add(stream)
        stack.add(initialIota)

        val newImage = harness.image.copy(stack = stack)

        return CastResult(
                ListIota(genNextCode),
                continuation,
                newImage,
                listOf(),
                ResolvedPatternType.EVALUATED,
                HexEvalSounds.THOTH
        )
    }

    override fun serializeToNBT(): NbtCompound {
        TODO("Not yet implemented")
    }

    override fun size(): Int = genNextCode.size()
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