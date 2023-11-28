package net.masterbw3.fivedimcasting.api.cells;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;

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
