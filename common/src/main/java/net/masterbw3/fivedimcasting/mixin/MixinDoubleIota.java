package net.masterbw3.fivedimcasting.mixin;

import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import net.masterbw3.fivedimcasting.mixinImpl.IMixinIota;
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Optional;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE;
import static net.masterbw3.fivedimcasting.common.lib.FiveDimCastingIotaTypes.QUATERNION;

@Mixin(DoubleIota.class)
public abstract class MixinDoubleIota extends Iota implements IMixinIota {

    protected MixinDoubleIota(@NotNull IotaType<?> type, @NotNull Object payload) {
        super(type, payload);
    }

    @Shadow(remap = false) public abstract double getDouble();

    @Override
    public <T extends Iota> Optional<T> tryCastTo(IotaType<T> iotaType) {
        if (iotaType == QUATERNION) {
            return Optional.of((T) new QuaternionIota(this.getDouble(), 0.0, 0.0, 0.0));
        } else if (this.getType() == iotaType) {
            return Optional.of((T) this);
        } else {
            return Optional.empty();
        }
    }
}