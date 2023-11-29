package net.masterbw3.fivedimcasting.api.cells;

import at.petrak.hexcasting.api.casting.iota.Iota;
import net.minecraft.nbt.NbtCompound;
import at.petrak.hexcasting.api.casting.iota.IotaType;

public class CellData {
    public static final String TAG_EXPIRATION_TIMESTAMP = "$MOD_ID:expiration_timestamp";
    public static final String TAG_STORED_IOTA = "$MOD_ID:stored_iota";

    private Iota storedIota;
    private long expirationTimestamp;

    public CellData(Iota storedIota, long lifetime) {
        this.storedIota = storedIota;
        this.expirationTimestamp = lifetime;
    }

    public Iota getStoredIota() {
        return storedIota;
    }

    public void setStoredIota(Iota storedIota) {
        this.storedIota = storedIota;
    }

    public long getExpirationTimestamp() {
        return expirationTimestamp;
    }

    public void setExpirationTimestamp(int lifetime) {
        this.expirationTimestamp = lifetime;
    }

    public NbtCompound serialize() {
        var nbt = new NbtCompound();
        nbt.putLong(TAG_EXPIRATION_TIMESTAMP, getExpirationTimestamp());
        nbt.put(TAG_STORED_IOTA, IotaType.serialize(getStoredIota()));
        return nbt;
    }
}
