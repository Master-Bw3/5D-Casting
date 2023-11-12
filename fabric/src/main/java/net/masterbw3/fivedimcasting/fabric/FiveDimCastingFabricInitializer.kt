package net.masterbw3.fivedimcasting.fabric

import at.petrak.hexcasting.common.lib.hex.HexActions
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.masterbw3.fivedimcasting.FiveDimCasting
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi
import net.masterbw3.fivedimcasting.api.cells.CellManager
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingActions
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import java.util.function.BiConsumer
import net.masterbw3.fivedimcasting.api.cells.CellSavedData
import net.minecraft.nbt.NbtCompound

object FiveDimCastingFabricInitializer : ModInitializer {
    const val FILE_CELL_MANAGER = "fivedimcasting_cell_manager"


    override fun onInitialize() {
        FiveDimCastingApi.LOGGER.info("Hello Fabric World!")

        initRegistries()

        FiveDimCasting.init()
    }

    private fun initListeners() {
        ServerLifecycleEvents.SERVER_STARTED.register {
            val cellSavedData = {nbt: NbtCompound -> CellSavedData(nbt, it.overworld)}
            val savedData = it.overworld.persistentStateManager.getOrCreate(cellSavedData, ::CellSavedData, FILE_CELL_MANAGER)
            savedData.isDirty = true
        }
        ServerLifecycleEvents.SERVER_STOPPING.register {
            val cellSavedData = {nbt: NbtCompound -> CellSavedData(nbt, it.overworld)}
            val savedData = it.overworld.persistentStateManager.getOrCreate(cellSavedData, ::CellSavedData, FILE_CELL_MANAGER)
            CellManager.shouldClearOnWrite = true
            savedData.isDirty = true
        }
    }

    private fun initRegistries() {
        FiveDimCastingActions.register(bind(HexActions.REGISTRY))
        FiveDimCastingIotaTypes.registerTypes(bind(HexIotaTypes.REGISTRY))
    }

    private fun <T> bind(registry: Registry<in T>): BiConsumer<T, Identifier> =
        BiConsumer<T, Identifier> { t, id -> Registry.register(registry, id, t) }
}