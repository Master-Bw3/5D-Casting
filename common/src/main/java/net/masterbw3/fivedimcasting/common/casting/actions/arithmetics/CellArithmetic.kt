package net.masterbw3.fivedimcasting.common.casting.actions.arithmetics

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic.APPEND
import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic.UNCONS
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.math.HexPattern
import net.masterbw3.fivedimcasting.common.casting.actions.arithmetics.operator.cell.OperatorGetCellValue
import net.masterbw3.fivedimcasting.common.casting.actions.arithmetics.operator.cell.OperatorModifyCellValue

object CellArithmetic : Arithmetic {

    private val OPS = listOf(
        APPEND,
        UNCONS, //temporary, replace with SPLAT

    )

    override fun arithName(): String = "cell_ops"


    override fun opTypes(): Iterable<HexPattern> = CellArithmetic.OPS


    override fun getOperator(pattern: HexPattern?): Operator =
        when (pattern) {
            APPEND -> OperatorModifyCellValue
            UNCONS -> {OperatorGetCellValue}
            else -> throw InvalidOperatorException("$pattern is not a valid operator in Arithmetic $this.")
        }


}