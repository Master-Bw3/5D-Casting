package net.masterbw3.fivedimcasting.common.casting.actions.arithmetics

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.math.HexPattern
import net.masterbw3.fivedimcasting.common.casting.actions.arithmetics.operator.stream.OperatorDeconstruct
import net.masterbw3.fivedimcasting.common.casting.actions.arithmetics.operator.stream.OperatorStreamIndex
import net.masterbw3.fivedimcasting.common.casting.actions.arithmetics.operator.stream.OperatorStreamSlice

object StreamArithmetic : Arithmetic {
    private val OPS = listOf(
        Arithmetic.INDEX,
        Arithmetic.SLICE,
        Arithmetic.UNCONS
    )

    override fun arithName(): String = "quaternion_ops"


    override fun opTypes(): Iterable<HexPattern> = OPS


    override fun getOperator(pattern: HexPattern?): Operator = when (pattern) {

        Arithmetic.INDEX -> OperatorStreamIndex
        Arithmetic.SLICE -> OperatorStreamSlice
        Arithmetic.UNCONS -> OperatorDeconstruct
        else -> throw InvalidOperatorException("$pattern is not a valid operator in Arithmetic $this.")

    }
}