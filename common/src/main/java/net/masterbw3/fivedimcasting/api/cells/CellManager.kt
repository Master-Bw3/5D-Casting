package net.masterbw3.fivedimcasting.api.cells

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.utils.putCompound
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi.MOD_ID
import net.masterbw3.fivedimcasting.api.casting.iota.CellIota
import net.masterbw3.fivedimcasting.api.cells.CellData.TAG_EXPIRATION_TIMESTAMP
import net.masterbw3.fivedimcasting.api.cells.CellData.TAG_STORED_IOTA
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld

object CellManager {
    private const val TAG_CURRENT_CELL_NUM = "$MOD_ID:current_cell_num"
    private const val TAG_CELLS = "$MOD_ID:cells"
    private const val TAG_UNINITIALIZED_CELLS = "$MOD_ID:uninitialized_cells"

    private const val MAX_UNINITIALIZED_CELL_COUNT = 500_000;

    private var currentCellNum = 0

    @JvmField
    var shouldClearOnWrite = false

    var cells: MutableMap<Int, CellData> = mutableMapOf()

    var uninitializedCells: MutableList<Int> = mutableListOf();


    @JvmStatic
    fun makeCell(expirationTimestamp: Int): CellIota {
        val cell = CellIota(currentCellNum, expirationTimestamp)
        currentCellNum += 1
        return cell
    }

    @JvmStatic
    fun addToCells(index: Int, cellData: CellData) {
        cells.put(index, cellData)
    }

    @JvmStatic
    fun addToUninitializedCells(index: Int) {
        //this is just to prevent someone from creating a memory leak by spamming cell creation
        if (uninitializedCells.size >= MAX_UNINITIALIZED_CELL_COUNT) {
            uninitializedCells.remove(0)
        }

        uninitializedCells.add(index)
    }

    @JvmStatic
    fun readFromNbt(nbtCompound: NbtCompound, world: ServerWorld) {

        if (nbtCompound.contains(TAG_CURRENT_CELL_NUM))
            currentCellNum = nbtCompound.getInt(TAG_CURRENT_CELL_NUM)

        if (nbtCompound.contains(TAG_CELLS)) {
            val cellsTag = nbtCompound.getCompound(TAG_CELLS)

            for (cellStr in cellsTag.keys) {
                val storedIota = IotaType.deserialize(cellsTag.getCompound(cellStr).getCompound(TAG_STORED_IOTA), world)
                val expirationTimestamp = cellsTag.getCompound(cellStr).getLong(TAG_EXPIRATION_TIMESTAMP)

                val cellData = CellData(storedIota, expirationTimestamp)

                cells[cellStr.toInt()] = cellData
            }
        }

        if (nbtCompound.contains(TAG_UNINITIALIZED_CELLS)) {
            uninitializedCells = nbtCompound.getIntArray(TAG_UNINITIALIZED_CELLS).toMutableList()
        }
    }

    @JvmStatic
    fun writeToNbt(nbt: NbtCompound) {
        nbt.putInt(TAG_CURRENT_CELL_NUM, currentCellNum)

        val cellsTag = NbtCompound()
        for ((cell, cellData) in cells) {
            cellsTag.putCompound(cell.toString(), cellData.serialize())
        }
        nbt.putCompound(TAG_CELLS, cellsTag)

        nbt.putIntArray(TAG_UNINITIALIZED_CELLS, uninitializedCells)

        if (shouldClearOnWrite) {
            currentCellNum = 0
            cells.clear()
        }

    }

    @JvmStatic
    fun getStoredIota(index: Int): Iota {
        val cellData = cells.get(index);

        return if (cellData == null) {
            NullIota()
        } else {
            cellData.storedIota
        };
    }

    @JvmStatic
    fun setStoredIota(index: Int, iota: Iota) {
        val cellData = cells[index]
        if (cellData != null) {
            cellData.storedIota = iota
            cells[index] = cellData
        }
    }

    @JvmStatic
    fun isCellStored(index: Int): Boolean {
        return cells.contains(index)
    }

    @JvmStatic
    fun isCellExpired(index: Int): Boolean {
        return !uninitializedCells.contains(index) && !cells.contains(index)
    }

    @JvmStatic
    fun removeExpiredCells(world: ServerWorld) {
        val gameTime = world.time;

        cells = cells.filterValues { it.expirationTimestamp >= gameTime } as MutableMap
    }

}