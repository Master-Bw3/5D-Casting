package net.masterbw3.fivedimcasting.casting.actions.cell

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import net.masterbw3.fivedimcasting.api.cells.CellData
import net.masterbw3.fivedimcasting.api.cells.CellManager
import net.masterbw3.fivedimcasting.api.getCell

object OpModifyCellValue : ConstMediaAction {
    override val argc: Int
        get() = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val cell = args.getCell(0, argc)
        val iota = args[1]

        if (CellManager.isCellStored(cell.index)) {
            CellManager.setStoredIota(cell.index, iota)
        } else {
            CellManager.addToCells(cell.index, CellData(iota, cell.lifetime))
        }

        return listOf(cell)
    }

}