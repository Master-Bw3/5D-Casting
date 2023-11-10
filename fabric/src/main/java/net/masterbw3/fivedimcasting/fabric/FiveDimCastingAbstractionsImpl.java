package net.masterbw3.fivedimcasting.fabric;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FiveDimCastingAbstractionsImpl {
    /**
     * This is the actual implementation of {@link FiveDimCastingAbstractions#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
