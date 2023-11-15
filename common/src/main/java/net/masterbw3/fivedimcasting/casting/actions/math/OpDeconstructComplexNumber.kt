package net.masterbw3.fivedimcasting.casting.actions.math

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.DoubleIota
import at.petrak.hexcasting.api.casting.iota.Iota
import net.masterbw3.fivedimcasting.api.getComplexNumber

object OpDeconstructComplexNumber  : ConstMediaAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val q = args.getComplexNumber(0, argc)

        return listOf(q.re(), q.im()).map { x -> DoubleIota(x) }
    }
}