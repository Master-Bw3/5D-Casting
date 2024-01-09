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
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.masterbw3.fivedimcasting.api.casting.eval.vm.FrameDeconstruct
import net.masterbw3.fivedimcasting.api.casting.eval.vm.FrameIterate
import net.masterbw3.fivedimcasting.api.getStream
import net.masterbw3.fivedimcasting.common.lib.FiveDimCastingIotaTypes

object OperatorDeconstruct : Operator(1,
        IotaMultiPredicate.all(IotaPredicate.ofType(FiveDimCastingIotaTypes.STREAM))
) {

    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        val stack = image.stack.toMutableList()

        if (stack.size < 1)
            throw MishapNotEnoughArgs(1, 0)

        val stream = stack.getStream(stack.lastIndex)
        stack.removeLastOrNull()

        //get next iota without maps applied
        val frame1 = FrameIterate(
                null,
                0U,
                Pair(1U, 1U),
                true,
                emptyList<Iota>().toMutableList(),
                stream.frontVal,
                SpellList.LList(stream.genNextFunc),
                emptyList()
        )

        //get initial iota with maps applied
        val frame2 = FrameIterate(
                null,
                0U,
                Pair(0U, 0U),
                true,
                emptyList<Iota>().toMutableList(),
                stream.frontVal,
                SpellList.LList(stream.genNextFunc),
                stream.maps
        )


        val frame3 = FrameDeconstruct(
                SpellList.LList(stream.genNextFunc),
                stream.maps
        )

        val image2 = image.withUsedOp().copy(stack = stack)

        return OperationResult(
                image2,
                listOf(),
                continuation.pushFrame(frame3).pushFrame(frame2).pushFrame(frame1),
                HexEvalSounds.THOTH
        )
    }
}