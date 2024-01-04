package net.masterbw3.fivedimcasting.mixin;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import net.masterbw3.fivedimcasting.mixinImpl.IMixinIota;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(Iota.class)
public abstract class MixinIota implements IMixinIota {

    @Override
    public <T extends Iota> Optional<T> tryCastTo(IotaType<T> iotaType) {
        if (this.getType() != iotaType)
            return Optional.empty();
        else
            return Optional.of((T) (Object) this);
    }

    @Shadow(remap = false)
    public abstract IotaType<?> getType();
}
