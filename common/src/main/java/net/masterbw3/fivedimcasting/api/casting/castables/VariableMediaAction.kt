package net.masterbw3.fivedimcasting.api.casting.castables

import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.sideeffects.OperatorSideEffect
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds

interface VariableMediaAction : Action {
    val argc: Int
    val mediaCost: Long
        get() = 0

    fun execute(args: List<Iota>, env: CastingEnvironment): Pair<List<Iota>, Long>

    fun executeWithOpCount(args: List<Iota>, env: CastingEnvironment): Pair<CostMediaActionResult, Long> {
        val result: Pair<List<Iota>, Long> = this.execute(args, env)
        return Pair(CostMediaActionResult(result.first), result.second)
    }

    override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
        val stack = image.stack.toMutableList()

        if (this.argc > stack.size)
            throw MishapNotEnoughArgs(this.argc, stack.size)
        val args = stack.takeLast(this.argc)
        repeat(this.argc) { stack.removeLast() }
        val result = this.executeWithOpCount(args, env)
        stack.addAll(result.first.resultStack)

        val sideEffects = mutableListOf<OperatorSideEffect>(OperatorSideEffect.ConsumeMedia(result.second))

        val image2 = image.copy(stack = stack, opsConsumed = image.opsConsumed + result.first.opCount)
        return OperationResult(image2, sideEffects, continuation, HexEvalSounds.NORMAL_EXECUTE)
    }

    data class CostMediaActionResult(val resultStack: List<Iota>, val opCount: Long = 1)

}