package net.masterbw3.fivedimcasting.mixin;

import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE;
import static net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes.QUATERNION;

@Mixin(DoubleIota.class)
public abstract class MixinDoubleIota extends Iota {

    protected MixinDoubleIota(@NotNull IotaType<?> type, @NotNull Object payload) {
        super(type, payload);
    }

    @Shadow public abstract double getDouble();

    @Unique
    private final List<IotaType<?>> typesCastableTo = List.of(DOUBLE, QUATERNION);

    @Override
    public boolean isCastableTo(IotaType<?> iotaType) {
        return typesCastableTo.contains(iotaType);
    }

    @Override
    public <T extends Iota> T castTo(IotaType<T> iotaType) {
        if (iotaType == QUATERNION) {
            return (T) new QuaternionIota(this.getDouble(), 0.0, 0.0, 0.0);
        } else if (this.getType() == iotaType) {
            return (T) this;
        } else {
            throw new IllegalStateException("Attempting to downcast " + this + " to type: " + iotaType);
        }
    }
}
