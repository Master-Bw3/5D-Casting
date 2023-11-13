package net.masterbw3.fivedimcasting.casting.actions.cell

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import net.masterbw3.fivedimcasting.api.cells.CellManager
import net.masterbw3.fivedimcasting.api.getCell

object OpGetCellValue : ConstMediaAction {
    override val argc: Int
        get() = 1

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val cell = args.getCell(0, OpModifyCellValue.argc)

        return listOf(CellManager.getStoredIota(cell.lifetime))
    }
}