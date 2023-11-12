package net.masterbw3.fivedimcasting.api.cells;

import at.petrak.hexcasting.api.casting.iota.Iota;
import kotlin.Pair;
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;

public class CellSavedData extends PersistentState {

    public CellSavedData() {}


    public CellSavedData(NbtCompound nbt, ServerWorld world) {
        CellManager.readFromNbt(nbt, world);
    }


    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        CellManager.writeToNbt(nbt);

        return nbt;
    }
}
