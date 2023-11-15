package net.masterbw3.fivedimcasting.casting.actions.math

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.iota.Iota
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota

object OpConstructQuaternion : ConstMediaAction {

    override val argc = 4

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val x0 = args.getDouble(0, argc)
        val x1 = args.getDouble(1, argc)
        val x2 = args.getDouble(2, argc)
        val x3 = args.getDouble(3, argc)

        val quaternionIota = QuaternionIota(x0, x1, x2, x3)

        return listOf(quaternionIota)
    }
}