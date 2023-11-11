package net.masterbw3.fivedimcasting.casting.actions.continuum

import at.petrak.hexcasting.api.casting.*
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import net.masterbw3.fivedimcasting.api.casting.eval.vm.FrameIterate
import net.masterbw3.fivedimcasting.api.getContinuum

object OpContinuumIndex : Action {
    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        val stack = image.stack.toMutableList()

        if (stack.size < 2)
            throw MishapNotEnoughArgs(2, stack.size)

        val continuum = stack.getContinuum(stack.lastIndex - 1)
        val index = stack.getPositiveInt(stack.lastIndex)
        stack.removeLastOrNull()
        stack.removeLastOrNull()

        val frame = FrameIterate(
                null,
                0U,
                Pair(index.toUInt(), index.toUInt()),
                true,
                emptyList<Iota>().toMutableList(),
                continuum.frontVal,
                SpellList.LList(continuum.genNextFunc),
                continuum.maps
        )
        val image2 = image.withUsedOp().copy(stack = stack)

        return OperationResult(image2, listOf(), continuation.pushFrame(frame), HexEvalSounds.THOTH)
    }
}