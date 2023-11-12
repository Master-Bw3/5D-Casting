package net.masterbw3.fivedimcasting.api.cells

import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.utils.putCompound
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi.MOD_ID
import net.masterbw3.fivedimcasting.api.casting.iota.CellIota
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld

object CellManager {
    private var currentCellNum = 0

    @JvmField
    var shouldClearOnWrite = false

    val cells: MutableMap<Int, CellData> = mutableMapOf()


    @JvmStatic
    fun makeCell(lifetime: Int): CellIota {
        val cell = CellIota(currentCellNum, lifetime)
        currentCellNum += 1
        return cell
    }

    @JvmStatic
    fun addToCells(index: Int, cellData: CellData) {
        cells.put(index, cellData)
    }

    @JvmStatic
    fun readFromNbt(tag: NbtCompound, world: ServerWorld) {
        if (tag.contains(TAG_CURRENT_CELL_NUM))
            currentCellNum = tag.getInt(TAG_CURRENT_CELL_NUM)

        if (tag.contains(TAG_CELLS)) {
            val cellsTag = tag.getCompound(TAG_CELLS)

            for (cellStr in cellsTag.keys) {
                val cellData = CellData()
                cellData.lifetime = cellsTag.getCompound(cellStr).getInt("lifetime")
                cellData.storedIota = IotaType.deserialize(cellsTag.getCompound(cellStr).getCompound("stored_iota"), world)

                cells[cellStr.toInt()] = cellData
            }
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
        val cellData = cells[index];
        if (cellData != null) {
          cellData.storedIota = iota;
        }
    }

    @JvmStatic
    fun isCellStored(index: Int): Boolean {
        return cells.contains(index)
    }


    const val TAG_CURRENT_CELL_NUM = "$MOD_ID:current_cell_num"
    const val TAG_CELLS = "$MOD_ID:cells"

}