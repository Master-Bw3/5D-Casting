package net.masterbw3.fivedimcasting.casting.arithmetic.operator

import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.common.casting.arithmetic.operator.nextInt
import at.petrak.hexcasting.common.casting.arithmetic.operator.nextPositiveIntUnder
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes.CONTINUUM
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE

object OperatorContinuumIndex : Operator(
    2,
    IotaMultiPredicate.any(
        IotaPredicate.ofType(CONTINUUM),
        IotaPredicate.ofType(DOUBLE)
    )

) {
    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()
        val arg0 = it.nextInt(arity)
        val arg1 = it.nextPositiveIntUnder(Int.MAX_VALUE, arity)

        arg0.a?.let { return (arg1.asMatrix.divi(it)).asActionResult }
        arg1.a?.let { return (arg0.asMatrix.rdivi(it)).asActionResult }

        val mat0 = arg0.asMatrix
        val mat1 = arg1.asMatrix

        if (mat0.columns != mat1.rows)
            throw MishapInvalidIota.matrixWrongSize(iotas.last(), 0, mat0.columns, null)
        if (mat1.columns != mat1.rows)
            throw MishapInvalidIota.matrixWrongSize(iotas.last(), 0, mat1.rows, mat0.rows)
        return (mat0.mmul(Solve.pinv(mat1))).asActionResult
    }
}