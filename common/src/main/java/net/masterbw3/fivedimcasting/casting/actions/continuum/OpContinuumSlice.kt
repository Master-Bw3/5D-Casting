package net.masterbw3.fivedimcasting.casting.actions.continuum

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.getPositiveInt
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import net.masterbw3.fivedimcasting.api.casting.eval.vm.FrameIterate
import net.masterbw3.fivedimcasting.api.getContinuum

object OpContinuumSlice : Action {
    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        val stack = image.stack.toMutableList()

        if (stack.size < 3)
            throw MishapNotEnoughArgs(3, stack.size)

        val continuum = stack.getContinuum(stack.lastIndex - 2)
        val startIndex = stack.getPositiveInt(stack.lastIndex - 1)
        val endIndex = stack.getPositiveInt(stack.lastIndex)
        stack.removeLastOrNull()
        stack.removeLastOrNull()
        stack.removeLastOrNull()


        val maps = emptyList<SpellList>().toMutableList();
        continuum.maps.forEach { map ->
            maps.add(SpellList.LList(map))
        }

        val frame = FrameIterate(
                null,
                0U,
                Pair(startIndex.toUInt(), endIndex.toUInt()),
                false,
                emptyList<Iota>().toMutableList(),
                continuum.frontVal,
                SpellList.LList(continuum.genNextFunc),
                maps
        )
        val image2 = image.withUsedOp().copy(stack = stack)

        return OperationResult(image2, listOf(), continuation.pushFrame(frame), HexEvalSounds.THOTH)
    }
}