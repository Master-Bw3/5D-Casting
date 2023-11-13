package net.masterbw3.fivedimcasting.lib.hex;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.OperationAction;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import net.masterbw3.fivedimcasting.casting.actions.cell.OpCreateCell;
import net.masterbw3.fivedimcasting.casting.actions.cell.OpGetCellValue;
import net.masterbw3.fivedimcasting.casting.actions.cell.OpModifyCellValue;
import net.masterbw3.fivedimcasting.casting.actions.continuum.OpContinuumIndex;
import net.masterbw3.fivedimcasting.casting.actions.continuum.OpContinuumMap;
import net.masterbw3.fivedimcasting.casting.actions.continuum.OpContinuumSlice;
import net.masterbw3.fivedimcasting.casting.actions.continuum.OpMakeStream;
import net.masterbw3.fivedimcasting.casting.actions.eval.OpCoolerEval;
import net.masterbw3.fivedimcasting.casting.actions.math.OpCreateQuaternion;
import net.masterbw3.fivedimcasting.casting.actions.spells.OpCongrats;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static net.masterbw3.fivedimcasting.api.FiveDimCastingApi.modLoc;

public class FiveDimCastingActions extends HexActions {
    private static final Map<Identifier, ActionRegistryEntry> ACTIONS = new LinkedHashMap<>();

    public static final ActionRegistryEntry QUATERNION_CREATE = make("quaternion/create",
            new ActionRegistryEntry(HexPattern.fromAngles("edd", HexDir.NORTH_EAST), OpCreateQuaternion.INSTANCE));

    public static final ActionRegistryEntry CONGRATS = make("congrats",
            new ActionRegistryEntry(HexPattern.fromAngles("eed", HexDir.NORTH_EAST), OpCongrats.INSTANCE));

    public static final ActionRegistryEntry COOLER_EVAL = make("cooler_eval",
           new ActionRegistryEntry(HexPattern.fromAngles("eedd", HexDir.NORTH_EAST), OpCoolerEval.INSTANCE));

    public static final ActionRegistryEntry MAKE_STREAM = make("stream/make",
            new ActionRegistryEntry(HexPattern.fromAngles("aqqqaqwdaqqqaq", HexDir.NORTH_EAST), OpMakeStream.INSTANCE));

    public static final ActionRegistryEntry CONTINUUM_INDEX = make("continuum/index",
            new ActionRegistryEntry(HexPattern.fromAngles("deeede", HexDir.NORTH_EAST), OpContinuumIndex.INSTANCE));

    public static final ActionRegistryEntry CONTINUUM_SLICE = make("continuum/slice",
            new ActionRegistryEntry(HexPattern.fromAngles("qaeaqwdedd", HexDir.NORTH_EAST), OpContinuumSlice.INSTANCE));

    public static final ActionRegistryEntry CONTINUUM_MAP = make("continuum/map",
            new ActionRegistryEntry(HexPattern.fromAngles("dad", HexDir.NORTH_EAST), OpContinuumMap.INSTANCE));

    public static final ActionRegistryEntry CELL_CREATE = make("cell/create",
            new ActionRegistryEntry(HexPattern.fromAngles("w", HexDir.NORTH_EAST), OpCreateCell.INSTANCE));

    public static final ActionRegistryEntry CELL_MODIFY = make("cell/get",
            new ActionRegistryEntry(HexPattern.fromAngles("ww", HexDir.NORTH_EAST), OpGetCellValue.INSTANCE));

    public static final ActionRegistryEntry CELL_GET = make("cell/modify",
            new ActionRegistryEntry(HexPattern.fromAngles("www", HexDir.NORTH_EAST), OpModifyCellValue.INSTANCE));


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
