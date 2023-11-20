package net.masterbw3.fivedimcasting.api;

import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.common.base.Suppliers;
import java.util.function.Supplier;



public interface FiveDimCastingApi
{
    String MOD_ID = "fivedimcasting";
    Logger LOGGER = LogManager.getLogger(MOD_ID);

    Supplier<FiveDimCastingApi> INSTANCE = Suppliers.memoize(() -> {
        try {
            return (FiveDimCastingApi) Class.forName("net.masterbw3.fivedimcasting.FiveDimCastingApiImpl")
                    .getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            LogManager.getLogger().warn("Unable to find FiveDimCastingAPIImpl, using a dummy");
            return new FiveDimCastingApi() {
            };
        }
    });

    static FiveDimCastingApi instance() {
        return INSTANCE.get();
    }

    static Identifier modLoc(String s) {
        return new Identifier(MOD_ID, s);
    }

    /**
     * Location in the userdata of the list of mutable cells
     */
    String MUTABLE_CELLS_USERDATA = modLoc("mutable_cells").toString();
}