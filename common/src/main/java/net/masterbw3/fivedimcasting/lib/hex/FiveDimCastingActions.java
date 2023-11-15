package net.masterbw3.fivedimcasting.lib.hex;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import at.petrak.hexcasting.api.casting.castables.OperationAction;
import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import at.petrak.hexcasting.common.lib.hex.HexActions;
import net.masterbw3.fivedimcasting.casting.actions.cell.OpCreateCell;
import net.masterbw3.fivedimcasting.casting.actions.continuum.OpContinuumIndex;
import net.masterbw3.fivedimcasting.casting.actions.continuum.OpContinuumMap;
import net.masterbw3.fivedimcasting.casting.actions.continuum.OpContinuumSlice;
import net.masterbw3.fivedimcasting.casting.actions.continuum.OpMakeStream;
import net.masterbw3.fivedimcasting.casting.actions.math.*;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static net.masterbw3.fivedimcasting.api.FiveDimCastingApi.modLoc;

public class FiveDimCastingActions extends HexActions {
    private static final Map<Identifier, ActionRegistryEntry> ACTIONS = new LinkedHashMap<>();

    //math
    public static final ActionRegistryEntry CONSTRUCT_QUATERNION = make("construct_quaternion",
            new ActionRegistryEntry(HexPattern.fromAngles("weqqqqq", HexDir.NORTH_EAST), OpConstructQuaternion.INSTANCE));

    public static final ActionRegistryEntry DECONSTRUCT_QUATERNION = make("deconstruct_quaternion",
            new ActionRegistryEntry(HexPattern.fromAngles("wqeeeee", HexDir.NORTH_EAST), OpDeconstructQuaternion.INSTANCE));

    public static final ActionRegistryEntry CONSTRUCT_COMPLEX = make("construct_complex",
            new ActionRegistryEntry(HexPattern.fromAngles("aeqqqqq", HexDir.NORTH_EAST), OpConstructComplexNumber.INSTANCE));

    public static final ActionRegistryEntry DECONSTRUCT_COMPLEX = make("deconstruct_complex",
            new ActionRegistryEntry(HexPattern.fromAngles("aqeeeee", HexDir.NORTH_EAST), OpDeconstructComplexNumber.INSTANCE));

    public static final ActionRegistryEntry COERCE_DOUBLE = make("coerce_double",
            new ActionRegistryEntry(HexPattern.fromAngles("aaqa", HexDir.NORTH_EAST), OpCoerceDouble.INSTANCE));

    //streams
    public static final ActionRegistryEntry MAKE_STREAM = make("stream/make",
            new ActionRegistryEntry(HexPattern.fromAngles("aqqqaqwdaqqqaq", HexDir.NORTH_EAST), OpMakeStream.INSTANCE));

    public static final ActionRegistryEntry CONTINUUM_INDEX = make("continuum/index",
            new ActionRegistryEntry(HexPattern.fromAngles("deeede", HexDir.NORTH_EAST), OpContinuumIndex.INSTANCE));

    public static final ActionRegistryEntry CONTINUUM_SLICE = make("continuum/slice",
            new ActionRegistryEntry(HexPattern.fromAngles("qaeaqwdedd", HexDir.NORTH_EAST), OpContinuumSlice.INSTANCE));

    public static final ActionRegistryEntry CONTINUUM_MAP = make("continuum/map",
            new ActionRegistryEntry(HexPattern.fromAngles("dad", HexDir.NORTH_EAST), OpContinuumMap.INSTANCE));

    //cells
    public static final ActionRegistryEntry CELL_CREATE = make("cell/create",
            new ActionRegistryEntry(HexPattern.fromAngles("ded", HexDir.NORTH_EAST), OpCreateCell.INSTANCE));


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
