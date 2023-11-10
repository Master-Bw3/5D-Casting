package net.masterbw3.fivedimcasting.lib.hex;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.OperationAction;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.casting.actions.selectors.OpGetCaster;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import net.masterbw3.fivedimcasting.casting.patterns.continuum.OpMakeStream;
import net.masterbw3.fivedimcasting.casting.patterns.eval.OpCoolerEval;
import net.masterbw3.fivedimcasting.casting.patterns.spells.OpCongrats;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static net.masterbw3.fivedimcasting.api.FiveDimCastingApi.modLoc;

public class FiveDimCastingActions extends HexActions {
    private static final Map<Identifier, ActionRegistryEntry> ACTIONS = new LinkedHashMap<>();

    public static final ActionRegistryEntry CONGRATS = make("congrats",
            new ActionRegistryEntry(HexPattern.fromAngles("eed", HexDir.NORTH_EAST), OpCongrats.INSTANCE));

    public static final ActionRegistryEntry COOLER_EVAL = make("cooler_eval",
           new ActionRegistryEntry(HexPattern.fromAngles("eedd", HexDir.NORTH_EAST), OpCoolerEval.INSTANCE));

    public static final ActionRegistryEntry MAKE_STREAM = make("make_stream",
            new ActionRegistryEntry(HexPattern.fromAngles("www", HexDir.NORTH_EAST), OpMakeStream.INSTANCE));


    public static ActionRegistryEntry make(String name, ActionRegistryEntry are) {
        var old = ACTIONS.put(modLoc(name), are);
        if (old != null) {
            throw new IllegalArgumentException("Typo? Duplicate id " + name);
        }
        return are;
    }

    public static ActionRegistryEntry make(String name, OperationAction oa) {
        var are = new ActionRegistryEntry(oa.getPattern(), oa);
        var old = ACTIONS.put(modLoc(name), are);
        if (old != null) {
            throw new IllegalArgumentException("Typo? Duplicate id " + name);
        }
        return are;
    }

    public static void register(BiConsumer<ActionRegistryEntry, Identifier> r) {
        for (var e : ACTIONS.entrySet()) {
            r.accept(e.getValue(), e.getKey());
        }
    }
}
