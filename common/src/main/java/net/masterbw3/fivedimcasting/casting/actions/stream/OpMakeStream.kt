package net.masterbw3.fivedimcasting.casting.actions.stream

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.iota.Iota
import net.masterbw3.fivedimcasting.api.casting.iota.StreamIota


object OpMakeStream : ConstMediaAction {
    override val argc: Int
        get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val initialIota = args[0]
        val genNextCode = args.getList(1, argc).toMutableList()

        return listOf(
            StreamIota(
                initialIota,
                genNextCode,
                emptyList()
            )
        );
    }


}