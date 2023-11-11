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
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld

data class FrameMap(
        val data: List<Iota>,
        val remainingMaps: List<SpellList>,
        val currentMap: SpellList,
        val baseStack: List<Iota>,
        val acc: List<Iota>,
        val init: Boolean,
        val collectSingle: Boolean,
) : ContinuationFrame {
    override val type: ContinuationFrame.Type<*>
        get() = TODO("Not yet implemented")

    override fun breakDownwards(stack: List<Iota>): Pair<Boolean, List<Iota>> {
        return true to stack
    }

    override fun evaluate(
            continuation: SpellContinuation,
            level: ServerWorld, harness: CastingVM
    ): CastResult {
        var newCont = continuation
        var newImage = harness.image.withUsedOp()

        //start of a new map
        if (init) {
            var newCurrentMap = remainingMaps[0]
            var newRemainingMaps = remainingMaps.drop(1)

            var newData = data.drop(1)
            var element = data.first()

            newCont = newCont.pushFrame(FrameMap(
                    newData,
                    newRemainingMaps,
                    newCurrentMap,
                    baseStack,
                    acc,
                    false,
                    collectSingle
            ))

            newImage = newImage.copy(stack = listOf(element))

            newCont = newCont.pushFrame(FrameEvaluate(
                    currentMap,
                    true
            ))

            return CastResult(
                    ListIota(newCurrentMap),
                    newCont,
                    newImage,
                    emptyList(),
                    ResolvedPatternType.EVALUATED,
                    HexEvalSounds.THOTH
            )
        }

        //end of a map
        if (data.isEmpty()) {
            val newAcc = acc.toMutableList()
            val stackTop = newImage.stack.last()
            newAcc.add(stackTop)

            if (!remainingMaps.isEmpty()) {
                //if there are more maps
                newImage = newImage.copy(stack = emptyList())

                newCont = newCont.pushFrame(FrameMap(
                        acc,
                        remainingMaps,
                        SpellList.LList(emptyList()),
                        baseStack,
                        emptyList(),
                        true,
                        collectSingle

                ))

                return CastResult(
                        ListIota(currentMap),
                        newCont,
                        newImage,
                        listOf(),
                        ResolvedPatternType.EVALUATED,
                        HexEvalSounds.THOTH
                )
            } else {
                //end of all maps
                newImage = if (collectSingle) {
                    newImage.copy(stack = baseStack + listOf(newAcc.first()))
                } else {
                    newImage.copy(stack = baseStack + listOf(ListIota(acc)))
                }
                return CastResult(
                        ListIota(currentMap),
                        newCont,
                        newImage,
                        listOf(),
                        ResolvedPatternType.EVALUATED,
                        HexEvalSounds.THOTH
                )
            }


        } else {
            //iter next
            val newData = data.drop(1)
            val stackTop = newImage.stack.last()
            val element = data.first()
            val newAcc = acc.toMutableList()

            newAcc.add(stackTop)

            newCont = newCont.pushFrame(FrameMap(
                    newData,
                    remainingMaps,
                    currentMap,
                    baseStack,
                    newAcc,
                    false,
                    collectSingle
            ))

            newImage = newImage.copy(stack = listOf(element))

            newCont = newCont.pushFrame(FrameEvaluate(
                    currentMap,
                    true
            ))

            return CastResult(
                    ListIota(currentMap),
                    newCont,
                    newImage,
                    listOf(),
                    ResolvedPatternType.EVALUATED,
                    HexEvalSounds.THOTH
            )


        }
    }

    override fun serializeToNBT(): NbtCompound {
        TODO("Not yet implemented")
    }

    override fun size(): Int {
        TODO("Not yet implemented")
    }
}