package net.masterbw3.fivedimcasting.api.casting.iota;

import at.petrak.hexcasting.api.casting.SpellList;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.utils.HexUtils;
import at.petrak.hexcasting.api.utils.NbtListBuilder;
import net.masterbw3.fivedimcasting.FiveDimCasting;
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ContinuumIota extends Iota {
    private record Payload(Iota frontVal, List<Iota> genNextFunc, List<SpellList> maps) {
    }

    public ContinuumIota(Iota frontVal, List<Iota> genNextFunc, List<SpellList> maps) {
        super(FiveDimCastingIotaTypes.CONTINUUM, new Payload(frontVal, genNextFunc, maps));
    }

    public Iota getFrontVal() {
        return ((Payload) this.payload).frontVal;
    }

    public List<Iota> getGenNextFunc() {
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
        var tag = new NbtCompound();

        var mapsNBT = new NbtList();
        for (SpellList map : getMaps()) {
            var mapNBT = new NbtList();
            for (Iota iota : map) {
                mapNBT.add(IotaType.serialize(iota));
            }
            mapsNBT.add(mapNBT);
        }

        var genNextFuncNBT = new NbtList();
        for (Iota iota : getGenNextFunc()) {
            genNextFuncNBT.add(IotaType.serialize(iota));
        }


        tag.put("front_val", IotaType.serialize(getFrontVal()));
        tag.put("gen_next_func", genNextFuncNBT);
        tag.put("maps", mapsNBT);

        return tag;
    }

    public static IotaType<ContinuumIota> TYPE = new IotaType<>() {
        @Override
        public ContinuumIota deserialize(NbtElement tag, ServerWorld world) throws IllegalArgumentException {
            var ctag = HexUtils.downcast(tag, NbtCompound.TYPE);

            var frontValNBT = ctag.getCompound("front_val");
            var frontVal = IotaType.deserialize(frontValNBT, world);

            var genNextFuncNBT = ctag.getList("gen_next_func", NbtElement.COMPOUND_TYPE);

            List<Iota> genNextFunc = new ArrayList<>();
            for (NbtElement nbtElement : genNextFuncNBT) {
                var nbtCompound = HexUtils.downcast(nbtElement, NbtCompound.TYPE);
                var iota = IotaType.deserialize(nbtCompound, world);
                genNextFunc.add(iota);
            }

            List<SpellList> maps = new ArrayList<>();

            var mapsNBT = ctag.getList("maps", NbtElement.LIST_TYPE);
            for (NbtElement nbtElement : mapsNBT) {
                List<Iota> map = new ArrayList<>();
                var nbtList = HexUtils.downcast(nbtElement, NbtList.TYPE);
                for (NbtElement nbtElement2 : nbtList) {
                    var nbtCompound = HexUtils.downcast(nbtElement2, NbtCompound.TYPE);
                    var iota = IotaType.deserialize(nbtCompound, world);
                    map.add(iota);
                }
                maps.add(new SpellList.LList(map));
            }


            return new ContinuumIota(frontVal, genNextFunc, maps);
        }

        @Override
        public Text display(NbtElement nbtElement) {
            return Text.literal("Continuum");
        }

        @Override
        public int color() {
            return 0;
        }
    };
}
