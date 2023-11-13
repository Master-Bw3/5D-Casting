package net.masterbw3.fivedimcasting.fabric

import at.petrak.hexcasting.common.lib.hex.HexActions
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import at.petrak.hexcasting.xplat.IXplatAbstractions
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.masterbw3.fivedimcasting.FiveDimCasting
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi
import net.masterbw3.fivedimcasting.api.cells.CellManager
import net.masterbw3.fivedimcasting.api.cells.CellSavedData
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingActions
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingArithmetics
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import java.util.function.BiConsumer

object FiveDimCastingFabricInitializer : ModInitializer {
    const val FILE_CELL_MANAGER = "fivedimcasting_cell_manager"


    override fun onInitialize() {
        FiveDimCastingApi.LOGGER.info("Hello Fabric World!")

        initListeners()
        initRegistries()
        FiveDimCasting.init()
    }

    private fun initListeners() {
        ServerLifecycleEvents.SERVER_STARTED.register {
            val cellSavedData = {nbt: NbtCompound -> CellSavedData(nbt, it.overworld)}
            val savedData = it.overworld.persistentStateManager.getOrCreate(cellSavedData, ::CellSavedData, FILE_CELL_MANAGER)
            savedData.markDirty()
        }
        ServerLifecycleEvents.SERVER_STOPPING.register {
            val cellSavedData = {nbt: NbtCompound -> CellSavedData(nbt, it.overworld)}
            val savedData = it.overworld.persistentStateManager.getOrCreate(cellSavedData, ::CellSavedData, FILE_CELL_MANAGER)
            CellManager.shouldClearOnWrite = true
            savedData.markDirty()
        }
    }

    private fun initRegistries() {
        FiveDimCastingActions.register(bind(HexActions.REGISTRY))
        FiveDimCastingIotaTypes.registerTypes(bind(HexIotaTypes.REGISTRY))
        FiveDimCastingArithmetics.register(bind(IXplatAbstractions.INSTANCE.arithmeticRegistry))

    }

    private fun <T> bind(registry: Registry<in T>): BiConsumer<T, Identifier> =
        BiConsumer<T, Identifier> { t, id -> Registry.register(registry, id, t) }
}