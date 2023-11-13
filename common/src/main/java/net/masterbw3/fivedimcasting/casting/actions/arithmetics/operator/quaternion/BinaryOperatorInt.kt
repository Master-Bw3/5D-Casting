package net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.quaternion

import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.common.casting.arithmetic.operator.nextInt
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota
import net.masterbw3.fivedimcasting.api.utils.Quaternion
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.nextNumber
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes

class BinaryOperatorInt(val op: (a: Quaternion, b: Int) -> Quaternion) : Operator(2,
        IotaMultiPredicate.pair(
                IotaPredicate.or(IotaPredicate.ofType(HexIotaTypes.DOUBLE),
                        IotaPredicate.ofType(FiveDimCastingIotaTypes.QUATERNION)
                ),
                IotaPredicate.ofType(HexIotaTypes.DOUBLE)
        )) {
    override fun apply(iotas: MutableIterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()

        val a = it.nextNumber().quaternion
        val b = it.nextInt()

        val result = QuaternionIota(op(a, b))

        return listOf(result)

    }

}