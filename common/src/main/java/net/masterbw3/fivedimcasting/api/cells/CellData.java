package net.masterbw3.fivedimcasting.api.cells;

import at.petrak.hexcasting.api.casting.iota.Iota;
import net.minecraft.nbt.NbtCompound;
import at.petrak.hexcasting.api.casting.iota.IotaType;

public class CellData {
    private Iota storedIota;
    private int lifetime;

    public CellData(Iota storedIota, int lifetime) {
        this.storedIota = storedIota;
        this.lifetime = lifetime;
    }

    public Iota getStoredIota() {
        return storedIota;
    }

    public void setStoredIota(Iota storedIota) {
        this.storedIota = storedIota;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public NbtCompound serialize() {
        var nbt = new NbtCompound();
        nbt.putInt("lifetime", getLifetime());
        nbt.put("stored_iota", IotaType.serialize(getStoredIota()));
        return nbt;
    }
}
