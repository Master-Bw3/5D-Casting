package net.masterbw3.fivedimcasting.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.masterbw3.fivedimcasting.FiveDimCastingClient;

/**
 * Fabric client loading entrypoint.
 */
public class FiveDimCastingClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FiveDimCastingClient.init();
    }
}
