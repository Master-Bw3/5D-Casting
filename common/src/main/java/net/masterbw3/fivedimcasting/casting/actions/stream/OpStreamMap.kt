package net.masterbw3.fivedimcasting.casting.actions.stream

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota
import net.masterbw3.fivedimcasting.api.getStream

object OpStreamMap : ConstMediaAction {
    override val argc: Int
        get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val continuum = args.getStream(0, argc)
        val code = args.getList(1, argc)

        continuum.maps.add(code)

        return listOf(continuum)
    }

}