package net.masterbw3.fivedimcasting.mixin;

import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import net.masterbw3.fivedimcasting.mixinImpl.IMixinIota;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Operator.Companion.class)
public class MixinOperator {

    /**
     * @author Master_Bw3
     * @reason because
     */
    @Overwrite(remap = false)
    public final <T extends Iota> T downcast(Iota iota, IotaType<T> iotaType) {
        var x = ((IMixinIota) (Object) iota).tryCastTo(iotaType);
        if (x.isPresent()) {
            return x.get();
        } else {
            throw new IllegalStateException("Attempting to downcast " + iota + " to type: " + iotaType);
        }
    }
}