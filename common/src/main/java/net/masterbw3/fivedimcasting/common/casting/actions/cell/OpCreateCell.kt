package net.masterbw3.fivedimcasting.common.casting.actions.cell

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getPositiveInt
import at.petrak.hexcasting.api.casting.iota.Iota
import net.masterbw3.fivedimcasting.api.casting.castables.VariableMediaAction
import net.masterbw3.fivedimcasting.api.cells.CellManager

object OpCreateCell : VariableMediaAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): Pair<List<Iota>, Long> {
        val lifetime = args.getPositiveInt(0, argc)

        val cell = CellManager.makeCell(lifetime)
        CellManager.addToUninitializedCells(cell.index)

        val mediaCost = lifetime.toLong()
        return Pair(listOf(cell), mediaCost)
    }
}