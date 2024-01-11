package net.masterbw3.fivedimcasting.common.casting.actions.arithmetics.operator.quaternion

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota
import net.masterbw3.fivedimcasting.api.utils.Quaternion
import net.masterbw3.fivedimcasting.common.casting.actions.arithmetics.operator.nextQuaternion
import net.masterbw3.fivedimcasting.common.lib.FiveDimCastingIotaTypes

class BinaryOperatorQuaternion(val op: (a: Quaternion, b: Quaternion) -> Quaternion) : OperatorBasic(2,
        IotaMultiPredicate.all(
                IotaPredicate.or(IotaPredicate.ofType(HexIotaTypes.DOUBLE),
                        IotaPredicate.ofType(FiveDimCastingIotaTypes.QUATERNION)
                ))) {

    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()

        val a = it.nextQuaternion()
        val b = it.nextQuaternion()

        val result = QuaternionIota(op(a, b))

        return listOf(result)

    }

}