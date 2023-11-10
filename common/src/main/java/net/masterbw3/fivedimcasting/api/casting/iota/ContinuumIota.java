package net.masterbw3.fivedimcasting.api.casting.iota;

import at.petrak.hexcasting.api.casting.SpellList;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.utils.HexUtils;
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContinuumIota extends Iota {
    private record Payload(Iota frontVal, SpellList genNextFunc, List<SpellList> maps) {
    }

    public ContinuumIota(Iota frontVal, SpellList genNextFunc, List<SpellList> maps) {
        super(FiveDimCastingIotaTypes.CONTINUUM, new Payload(frontVal, genNextFunc, maps));
    }

    public Iota getFrontVal() {
        return ((Payload) this.payload).frontVal;
    }

    public SpellList getGenNextFunc() {
        return ((Payload) this.payload).genNextFunc;
    }

    public List<SpellList> getMaps() {
        return ((Payload) this.payload).maps;
    }



    @Override
    public boolean isTruthy() {
        return true;
    }

    @Override
    protected boolean toleratesOther(Iota iota) {
        // TODO: check if same or something idk
        return false;
    }

    @Override
    public @NotNull NbtElement serialize() {
        //TODO: serialize
        return null;
    }

    public static IotaType<ContinuumIota> TYPE = new IotaType<>() {
        @Override
        public ContinuumIota deserialize(NbtElement tag, ServerWorld world) throws IllegalArgumentException {

            //return new ContinuumIota();
            return null;
        }

        @Override
        public Text display(NbtElement nbtElement) {
            return null;
        }

        @Override
        public int color() {
            return 0;
        }


    };
}
