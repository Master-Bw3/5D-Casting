package net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.Continuum

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.getPositiveInt
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE
import net.masterbw3.fivedimcasting.api.casting.eval.vm.FrameIterate
import net.masterbw3.fivedimcasting.api.getContinuum
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes.CONTINUUM

object OperatorContinuumIndex : Operator(2,
    IotaMultiPredicate.pair(IotaPredicate.ofType(CONTINUUM), IotaPredicate.ofType(DOUBLE))
    ) {
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