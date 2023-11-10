package net.masterbw3.fivedimcasting.fabric

import at.petrak.hexcasting.common.lib.hex.HexActions
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.fabricmc.api.ModInitializer
import net.masterbw3.fivedimcasting.FiveDimCasting
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingActions
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import java.util.function.BiConsumer

object FiveDimCastingFabricInitializer : ModInitializer {
    override fun onInitialize() {
        FiveDimCastingApi.LOGGER.info("Hello Fabric World!")

        initRegistries()

        FiveDimCasting.init()
    }

    private fun initRegistries() {
        FiveDimCastingActions.register(bind(HexActions.REGISTRY))
        FiveDimCastingIotaTypes.registerTypes(bind(HexIotaTypes.REGISTRY))
    }

    private fun <T> bind(registry: Registry<in T>): BiConsumer<T, Identifier> =
        BiConsumer<T, Identifier> { t, id -> Registry.register(registry, id, t) }
}