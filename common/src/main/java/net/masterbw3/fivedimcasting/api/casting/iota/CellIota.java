package net.masterbw3.fivedimcasting.api.casting.iota;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.utils.HexUtils;
import net.masterbw3.fivedimcasting.mixinImpl.IMixinIota;
import net.masterbw3.fivedimcasting.api.cells.CellManager;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes.CELL;

public class CellIota extends Iota implements IMixinIota {
    public static String TAG_INDEX = "index";
    public static String TAG_LIFETIME = "lifetime";

    private record Payload(int index, int lifetime) {
    }

    public CellIota(int index, int lifetime) {
        super(CELL, new Payload(index, lifetime));
    }

    public int getIndex() {
        return ((Payload) this.payload).index;
    }

    public int getLifetime() {
        return ((Payload) this.payload).lifetime;
    }

    public Iota getStoredIota() {
        return CellManager.getStoredIota(getIndex());
    }


    @Override
    public <T extends Iota> Optional<T> tryCastTo(IotaType<T> iotaType) {
        if (iotaType == CELL) {
            return Optional.of((T) this);
        } else {
            var storedIota = this.getStoredIota();
            if (storedIota.getType() != CELL)
                return ((IMixinIota) (Object) this.getStoredIota()).tryCastTo(iotaType);
            else return Optional.empty();
        }
    }

    @Override
    public boolean isTruthy() {
        return (CellManager.isCellStored(getIndex()));
    }

    @Override
    protected boolean toleratesOther(Iota that) {
        return typesMatch(this, that)
                && that instanceof CellIota dent
                && this.getIndex() == dent.getIndex();
    }

    @Override
    public @NotNull NbtElement serialize() {
        var tag = new NbtCompound();
        tag.putInt("index", getIndex());
        tag.putInt("lifetime", getLifetime());

        return tag;
    }


    public static IotaType<CellIota> TYPE = new IotaType<>() {

        private CellIota deserialize(NbtElement nbtElement) throws IllegalArgumentException {
            var ctag = HexUtils.downcast(nbtElement, NbtCompound.TYPE);

            var index = ctag.getInt("index");
            var lifetime = ctag.getInt("lifetime");

            return new CellIota(index, lifetime);
        }

        @Override
        public CellIota deserialize(NbtElement nbtElement, ServerWorld world) throws IllegalArgumentException {
            return deserialize(nbtElement);
        }

        @Override
        public Text display(NbtElement nbtElement) {
            var out = Text.empty();
            var cell = deserialize(nbtElement);

            out.append(Text.literal("Cell("));
            out.append(Text.literal(cell.getIndex() + ", "));
            out.append(cell.getStoredIota().getType().typeName());
            out.append(")");

            return out;
        }

        @Override
        public int color() {
            return 0;
        }
    };
}
