package net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.cell

import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.iota.Iota
import net.masterbw3.fivedimcasting.api.cells.CellManager
import net.masterbw3.fivedimcasting.casting.actions.arithmetics.operator.nextCell
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes

object OperatorGetCellValue : Operator (1,
    IotaMultiPredicate.all(
        IotaPredicate.ofType(FiveDimCastingIotaTypes.CELL),
    )) {

    override fun apply(iotas: Iterable<Iota>, env: CastingEnvironment): Iterable<Iota> {
        val it = iotas.iterator().withIndex()

        val cell = it.nextCell()

        return listOf(CellManager.getStoredIota(cell.index))
    }
}