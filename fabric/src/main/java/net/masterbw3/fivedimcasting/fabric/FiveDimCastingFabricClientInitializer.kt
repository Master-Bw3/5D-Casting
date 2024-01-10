package net.masterbw3.fivedimcasting.fabric

import net.fabricmc.api.ClientModInitializer
import net.masterbw3.fivedimcasting.FiveDimCastingClient
import net.masterbw3.fivedimcasting.fabric.network.FabricPacketHandler

object FiveDimCastingFabricClientInitializer : ClientModInitializer {
    override fun onInitializeClient() {
        FabricPacketHandler.initClient()
        FiveDimCastingClient.init()
    }
}