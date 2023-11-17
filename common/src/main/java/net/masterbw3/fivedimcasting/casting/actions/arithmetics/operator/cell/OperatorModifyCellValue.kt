package net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.cell

import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBasic
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import net.masterbw3.fivedimcasting.api.cells.CellData
import net.masterbw3.fivedimcasting.api.cells.CellManager
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.nextCell
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes

object OperatorModifyCellValue : OperatorBasic(2,
    IotaMultiPredicate.Pair(
        IotaPredicate.ofType(FiveDimCastingIotaTypes.CELL),
        IotaPredicate.TRUE
    )) {

    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()

        val cell = it.nextCell()
        val iota = it.next().value

        if (CellManager.isCellStored(cell.index)) {
            CellManager.setStoredIota(cell.index, iota)
        } else {
            CellManager.addToCells(cell.index, CellData(iota, cell.lifetime))
        }
        return listOf(cell)
    }
}