package net.masterbw3.fivedimcasting.fabric


import at.petrak.hexcasting.common.lib.hex.HexActions
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import at.petrak.hexcasting.fabric.FabricHexInitializer
import at.petrak.hexcasting.xplat.IXplatAbstractions
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.masterbw3.fivedimcasting.FiveDimCasting
import net.masterbw3.fivedimcasting.api.cells.CellManager
import net.masterbw3.fivedimcasting.api.cells.CellSavedData
import net.masterbw3.fivedimcasting.common.lib.*
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.function.BiConsumer


object FiveDimCastingFabricInitializer : ModInitializer {
    const val FILE_CELL_MANAGER = "fivedimcasting_cell_manager"

    private val FIVE_DIM_CASTING_ITEM_GROUP: ItemGroup = FabricItemGroup.builder()
        .icon { ItemStack(Registries.ITEM.get(Identifier("minecraft:dirt"))) }
        .displayName(Text.translatable("itemGroup.tutorial.test_group"))
        .build()


    override fun onInitialize() {
        FabricHexInitializer
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
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register { tab, entries ->
            FiveDimCastingItems.registerItemCreativeTab(entries, tab)
        }
    }

    private fun initRegistries() {
        FiveDimCastingActions.register(bind(HexActions.REGISTRY))
        FiveDimCastingIotaTypes.registerTypes(bind(HexIotaTypes.REGISTRY))
        FiveDimCastingArithmetics.register(bind(IXplatAbstractions.INSTANCE.arithmeticRegistry))
        FiveDimCastingCreativeTabs.registerCreativeTabs(bind(Registries.ITEM_GROUP))
        FiveDimCastingItems.registerItems(bind(Registries.ITEM))

    }

    private fun <T> bind(registry: Registry<in T>): BiConsumer<T, Identifier> =
        BiConsumer<T, Identifier> { t, id -> Registry.register(registry, id, t) }
}