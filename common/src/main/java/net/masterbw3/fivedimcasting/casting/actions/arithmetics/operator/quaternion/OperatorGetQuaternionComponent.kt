package net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.quaternion

import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapInternalException
import at.petrak.hexcasting.common.casting.arithmetic.operator.nextPositiveIntUnderInclusive
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.nextQuaternion
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes

object OperatorGetQuaternionComponent : Operator(2, IotaMultiPredicate.all(
        IotaPredicate.or(IotaPredicate.ofType(HexIotaTypes.DOUBLE),
                IotaPredicate.ofType(FiveDimCastingIotaTypes.QUATERNION)
        ))) {
    override fun apply(iotas: MutableIterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()

        val quaternion = it.nextQuaternion(0)
        val index = it.nextPositiveIntUnderInclusive(1, 3)

        val result = when(index) {
            0 -> quaternion.x0
            1 -> quaternion.x1
            2 -> quaternion.x2
            3 -> quaternion.x3
            else -> {throw MishapInternalException(Exception())
            }
        }


        return result.asActionResult
    }
}