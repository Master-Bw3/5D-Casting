package net.masterbw3.fivedimcasting.common.casting.actions.arithmetics.operator.stream

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
import net.masterbw3.fivedimcasting.api.getStream
import net.masterbw3.fivedimcasting.common.lib.FiveDimCastingIotaTypes.STREAM

object OperatorStreamIndex : Operator(2,
    IotaMultiPredicate.pair(IotaPredicate.ofType(STREAM), IotaPredicate.ofType(DOUBLE))
    ) {
    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        val stack = image.stack.toMutableList()

        if (stack.size < 2)
            throw MishapNotEnoughArgs(2, stack.size)

        val stream = stack.getStream(stack.lastIndex - 1)
        val index = stack.getPositiveInt(stack.lastIndex)
        stack.removeLastOrNull()
        stack.removeLastOrNull()

        val frame = FrameIterate(
            null,
            0U,
            Pair(index.toUInt(), index.toUInt()),
            true,
            emptyList<Iota>().toMutableList(),
            stream.frontVal,
            SpellList.LList(stream.genNextFunc),
            stream.maps
        )
        val image2 = image.withUsedOp().copy(stack = stack)

        return OperationResult(image2, listOf(), continuation.pushFrame(frame), HexEvalSounds.THOTH)
    }

}