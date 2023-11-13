package net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.quaternion


import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.common.casting.arithmetic.operator.nextDouble
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota
import net.masterbw3.fivedimcasting.api.utils.Quaternion
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.nextNumber
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes

class BinaryOperatorDouble(val op: (a: Quaternion, b: Double) -> Quaternion) : Operator(2,
        IotaMultiPredicate.pair(
                IotaPredicate.or(IotaPredicate.ofType(HexIotaTypes.DOUBLE),
                        IotaPredicate.ofType(FiveDimCastingIotaTypes.QUATERNION)
                ),
                IotaPredicate.ofType(HexIotaTypes.DOUBLE)
        )) {
    override fun apply(iotas: MutableIterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()

        val a = it.nextNumber().quaternion
        val b = it.nextDouble()

        val result = QuaternionIota(op(a, b))

        return listOf(result)

    }

}