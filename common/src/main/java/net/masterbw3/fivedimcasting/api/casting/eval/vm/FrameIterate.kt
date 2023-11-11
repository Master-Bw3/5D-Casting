package net.masterbw3.fivedimcasting.api.casting.eval.vm

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.eval.CastResult
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType
import at.petrak.hexcasting.api.casting.eval.vm.*
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.utils.getList
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.masterbw3.fivedimcasting.FiveDimCasting
import net.masterbw3.fivedimcasting.api.utils.NBTBuilder
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.server.world.ServerWorld

data class FrameIterate(
        val baseStack: List<Iota>?,
        val index: UInt,
        val collect: Pair<UInt, UInt>,
        val collectSingle: Boolean,
        val acc: List<Iota>,
        val initialIota: Iota,
        val genNextCode: SpellList,
        val maps: List<SpellList>,
) : ContinuationFrame {
    // Discard this frame and keep discarding frames.
    override fun breakDownwards(stack: List<Iota>) = true to stack

    // Step the list of patterns, evaluating a single one.
    override fun evaluate(
            continuation: SpellContinuation,
            level: ServerWorld,
            harness: CastingVM
    ): CastResult {
        var newCont = continuation
        var newImage = harness.image.withUsedOp()
        val newAcc = acc.toMutableList();

        if (index >= collect.first && index <= collect.second) {
            //if index in collect range, push top of stack to accumulator
            if (baseStack == null) {
                //on first just push the inital value
                newAcc.add(initialIota)
            } else {
                //else push top of stack (or null if stack is empty)
                val stackTop = getStackTop(harness)
                newAcc.add(stackTop)
            }
        }

        var newBaseStack = baseStack ?: harness.image.stack

        if (index >= collect.second) {
            //if frame is last in range, apply maps
            if (maps.isEmpty()) {
                newImage = if (collectSingle) {
                    newImage.copy(stack = newBaseStack + listOf(newAcc.first()))
                } else {
                    newImage.copy(stack = newBaseStack + listOf(ListIota(acc)))
                }
                return CastResult(
                        ListIota(genNextCode),
                        newCont,
                        newImage,
                        listOf(),
                        ResolvedPatternType.EVALUATED,
                        HexEvalSounds.THOTH
                )
            } else {
                newCont = newCont.pushFrame(FrameMap(
                        newAcc,
                        maps,
                        SpellList.LList(emptyList()),
                        newBaseStack,
                        emptyList(),
                        true,
                        collectSingle
                ))
                return CastResult(
                        ListIota(genNextCode),
                        newCont,
                        newImage,
                        listOf(),
                        ResolvedPatternType.EVALUATED,
                        HexEvalSounds.THOTH
                )
            }

        } else {
            var result = if (baseStack == null) {
                initialIota
            } else {
                getStackTop(harness)
            }
            newImage = newImage.copy(stack = listOf(result))

            newCont = newCont.pushFrame(FrameIterate(
                    newBaseStack,
                    (index + 1U),
                    collect,
                    collectSingle,
                    newAcc,
                    result,
                    genNextCode,
                    maps)
            ).pushFrame(FrameEvaluate(genNextCode, true))

            return CastResult(
                    ListIota(genNextCode),
                    newCont,
                    newImage,
                    listOf(),
                    ResolvedPatternType.EVALUATED,
                    HexEvalSounds.THOTH
            )
        }
    }

    private fun getStackTop(harness: CastingVM): Iota {
        return if (!harness.image.stack.isEmpty()) {
            harness.image.stack.last()
        } else {
            NullIota()
        }
    }

    override fun serializeToNBT() = NBTBuilder {
        TODO()
    }

    // TODO: actual size 
    override fun size() = genNextCode.size()

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