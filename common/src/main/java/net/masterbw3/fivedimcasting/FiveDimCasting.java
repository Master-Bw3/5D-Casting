package net.masterbw3.fivedimcasting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static net.masterbw3.fivedimcasting.api.FiveDimCastingApi.MOD_ID;

/**
 * This is effectively the loading entrypoint for most of your code, at least
 * if you are using Architectury as intended.
 */
public class FiveDimCasting {
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


    public static void init() {
        LOGGER.info("5D Casting says hello!");

    }


}
