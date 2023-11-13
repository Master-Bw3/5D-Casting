package net.masterbw3.fivedimcasting.api.casting.iota;

import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.utils.HexUtils;
import net.masterbw3.fivedimcasting.api.utils.Quaternion;
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class QuaternionIota extends Iota {

    public static final double TOLERANCE = 0.0001;


    private record Payload(double x0, double x1, double x2, double x3) {
    }

    public QuaternionIota(double x0, double x1, double x2, double x3) {
        super(FiveDimCastingIotaTypes.QUATERNION, new Payload(x0, x1, x2, x3));
    }

    public QuaternionIota(Quaternion quaternion) {
        super(FiveDimCastingIotaTypes.QUATERNION, new Payload(quaternion.x0(), quaternion.x1(), quaternion.x2(), quaternion.x3()));
    }
    public double getX0() {
        return ((Payload) this.payload).x0;
    }

    public double getX1() {
        return ((Payload) this.payload).x1;
    }

    public double getX2() {
        return ((Payload) this.payload).x2;
    }

    public double getX3() {
        return ((Payload) this.payload).x3;
    }

    public Quaternion getQuaternion() {
        return new Quaternion(getX0(), getX1(), getX2(), getX3());

    }

    @Override
    public boolean isTruthy() {
        return !Objects.equals(getQuaternion(), new Quaternion(0, 0, 0, 0));
    }

    @Override
    protected boolean toleratesOther(Iota that) {
        return (typesMatch(this, that)
                && that instanceof QuaternionIota dent
                && tolerates(this.getQuaternion(), dent.getQuaternion()));
    }

    public static boolean tolerates(Quaternion a, Quaternion b) {
        return Math.abs(a.x0() - b.x0()) < TOLERANCE
                && Math.abs(a.x1() - b.x1()) < TOLERANCE
                && Math.abs(a.x2() - b.x2()) < TOLERANCE
                && Math.abs(a.x3() - b.x3()) < TOLERANCE;
    }

    @Override
    public @NotNull NbtElement serialize() {
        var tag = new NbtCompound();
        tag.putDouble("x0", getX0());
        tag.putDouble("x1", getX1());
        tag.putDouble("x2", getX2());
        tag.putDouble("x3", getX3());


        return tag;
    }

    public static IotaType<QuaternionIota> TYPE = new IotaType<>() {

        private QuaternionIota deserialize(NbtElement nbtElement) throws IllegalArgumentException {
            var ctag = HexUtils.downcast(nbtElement, NbtCompound.TYPE);

            var x0 = ctag.getDouble("x0");
            var x1 = ctag.getDouble("x1");
            var x2 = ctag.getDouble("x2");
            var x3 = ctag.getDouble("x3");


            return new QuaternionIota(x0, x1, x2, x3);
        }

        @Override
        public QuaternionIota deserialize(NbtElement nbtElement, ServerWorld world) throws IllegalArgumentException {
            return deserialize(nbtElement);
        }

        @Override
        public Text display(NbtElement nbtElement) {
            var out = net.minecraft.text.Text.empty();
            var quaternion = deserialize(nbtElement);

            out.append(net.minecraft.text.Text.literal(Double.toString(quaternion.getX0())));
            if (quaternion.getX2() != 0.0 || quaternion.getX3() != 0.0) {
                out.append(net.minecraft.text.Text.literal(" + "));
                out.append(net.minecraft.text.Text.literal(quaternion.getX1() + "i"));
                out.append(net.minecraft.text.Text.literal(" + "));
                out.append(net.minecraft.text.Text.literal(quaternion.getX2() + "j"));
                out.append(net.minecraft.text.Text.literal(" + "));
                out.append(net.minecraft.text.Text.literal(quaternion.getX3() + "k"));
            } else if (quaternion.getX1() != 0.0) {
                out.append(net.minecraft.text.Text.literal(" + "));
                out.append(net.minecraft.text.Text.literal(quaternion.getX1() + "i"));
            }


            return out.formatted(Formatting.GREEN);
        }

        @Override
        public int color() {
            return DoubleIota.TYPE.color();
        }
    };


}
