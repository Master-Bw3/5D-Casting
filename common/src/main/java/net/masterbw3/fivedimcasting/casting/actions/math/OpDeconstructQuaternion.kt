package net.masterbw3.fivedimcasting.casting.actions.math

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import net.masterbw3.fivedimcasting.api.getQuaternion

object OpDeconstructQuaternion : ConstMediaAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val q = args.getQuaternion(0, argc)

        return listOf(q.x0, q.x1, q.x2, q.x3).map { x -> DoubleIota(x) }
    }
}