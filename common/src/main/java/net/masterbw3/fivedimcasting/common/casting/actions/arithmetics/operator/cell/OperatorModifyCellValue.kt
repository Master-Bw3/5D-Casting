package net.masterbw3.fivedimcasting.common.casting.actions.arithmetics.operator.cell

import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi.MUTABLE_CELLS_USERDATA
import net.masterbw3.fivedimcasting.api.casting.mishaps.MishapCantMutateCell
import net.masterbw3.fivedimcasting.api.cells.CellData
import net.masterbw3.fivedimcasting.api.cells.CellManager
import net.masterbw3.fivedimcasting.common.casting.actions.arithmetics.operator.nextCell
import net.masterbw3.fivedimcasting.common.lib.FiveDimCastingIotaTypes
import java.util.function.Consumer

object OperatorModifyCellValue : Operator(2,
    IotaMultiPredicate.Pair(
        IotaPredicate.ofType(FiveDimCastingIotaTypes.CELL),
        IotaPredicate.TRUE
    )) {

    fun apply(iotas: Iterable<Iota>, mutableCells: IntArray, env: CastingEnvironment): Pair<Iterable<Iota>, IntArray> {
        val it = iotas.iterator().withIndex()

        val cell = it.nextCell()
        val iota = it.next().value
        val newMutableCells = mutableCells.toMutableList()

        CellManager.removeExpiredCells(env.world)

        if (CellManager.isCellExpired(cell.index)) {
            //TODO: make a new mishap for this
            throw MishapCantMutateCell()
        }
        else if (CellManager.isCellStored(cell.index)) {
            if (mutableCells.contains(cell.index)) {
                CellManager.setStoredIota(cell.index, iota)
            } else {
                //if cell was not added to the list of stored cells in this cast
                //then it is locked and can't be mutated
                throw MishapCantMutateCell()
            }
        } else {
            //cell was never written to before and should be added to CellManager
            CellManager.uninitializedCells.remove(cell.index)

            CellManager.addToCells(cell.index, CellData(iota, cell.lifetime + env.world.time))
            newMutableCells.add(cell.index)
        }
        return Pair(listOf(cell), newMutableCells.toIntArray())
    }

    override fun operate(
        env: CastingEnvironment,
        image: CastingImage,
        continuation: SpellContinuation
    ): OperationResult {
        val stack = image.stack.toMutableList()
        val args = stack.takeLast(arity)
        repeat(arity) { stack.removeLast() }

        val mutableCells = if (image.userData.contains(MUTABLE_CELLS_USERDATA)) {
            image.userData.getIntArray(MUTABLE_CELLS_USERDATA)
        } else {
            IntArray(0)
        }

        val (ret, newMutableCells) = apply(args, mutableCells, env)
        ret.forEach(Consumer { e: Iota -> stack.add(e) })

        image.userData.putIntArray(MUTABLE_CELLS_USERDATA, newMutableCells)

        val image2 = image.copy(stack = stack, opsConsumed = image.opsConsumed + 1)
        return OperationResult(image2, listOf(), continuation, HexEvalSounds.NORMAL_EXECUTE)
    }
}