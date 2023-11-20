package net.masterbw3.fivedimcasting.casting.actions.arithmetics

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.math.HexPattern
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.continuum.OperatorContinuumIndex
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.continuum.OperatorContinuumSlice

object ContinuumArithmetic : Arithmetic {
    private val OPS = listOf(
        Arithmetic.INDEX,
        Arithmetic.SLICE
    )

    override fun arithName(): String = "quaternion_ops"


    override fun opTypes(): Iterable<HexPattern> = OPS


    override fun getOperator(pattern: HexPattern?): Operator = when (pattern) {

        Arithmetic.INDEX -> OperatorContinuumIndex
        Arithmetic.SLICE -> OperatorContinuumSlice
        else -> throw InvalidOperatorException("$pattern is not a valid operator in Arithmetic $this.")

    }
}