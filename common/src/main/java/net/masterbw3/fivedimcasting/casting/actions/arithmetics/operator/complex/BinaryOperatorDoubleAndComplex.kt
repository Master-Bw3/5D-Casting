package net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.complex


import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota
import at.petrak.hexcasting.common.casting.arithmetic.operator.nextDouble
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota
import net.masterbw3.fivedimcasting.api.utils.Complex
import net.masterbw3.fivedimcasting.api.utils.Quaternion
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.nextComplexNumber
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.nextQuaternion
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes

class BinaryOperatorDoubleAndComplex(val op: (a: Double, b: Complex) -> Complex) : Operator(2,
    IotaMultiPredicate.all(
        IotaPredicate.or(IotaPredicate.ofType(HexIotaTypes.DOUBLE),
            IotaPredicate.ofType(FiveDimCastingIotaTypes.QUATERNION)
        ))) {
    override fun apply(iotas: MutableIterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()

        val a = it.nextDouble()
        val b = it.nextComplexNumber()

        if (a <= 0) {
            throw MishapInvalidIota.ofType(DoubleIota(a), 1, "positive_number")
        }

        val result = op(a, b)

        val iota = QuaternionIota(result.re(), result.im(), 0.0, 0.0)

        return listOf(iota)

    }

}