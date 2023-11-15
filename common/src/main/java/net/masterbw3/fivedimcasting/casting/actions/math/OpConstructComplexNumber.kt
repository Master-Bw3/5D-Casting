package net.masterbw3.fivedimcasting.casting.actions.math

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.iota.Iota
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota

object OpConstructComplexNumber : ConstMediaAction {
    override val argc: Int
        get() = 2;

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val x0 = args.getDouble(0, OpConstructQuaternion.argc)
        val x1 = args.getDouble(1, OpConstructQuaternion.argc)

        val quaternionIota = QuaternionIota(x0, x1, 0.0, 0.0)

        return listOf(quaternionIota)
    }
}