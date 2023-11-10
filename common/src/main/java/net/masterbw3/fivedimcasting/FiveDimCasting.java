package net.masterbw3.fivedimcasting;

import at.petrak.hexcasting.common.lib.hex.HexActions;
import net.minecraft.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is effectively the loading entrypoint for most of your code, at least
 * if you are using Architectury as intended.
 */
public class FiveDimCasting {
    public static final String MOD_ID = "fivedimcasting";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


    public static void init() {
        LOGGER.info("Hex Dummy says hello!");

    }


}
