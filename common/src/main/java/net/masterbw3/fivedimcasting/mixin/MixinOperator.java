package net.masterbw3.fivedimcasting.mixin;

import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota;
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Operator.class)
public class MixinOperator {

    /**
     * @author Master_Bw3
     * @reason because
     */
    @Overwrite(remap = false)
    public static <T extends Iota> T downcast(Iota iota, IotaType<T> iotaType) {
        if (iota.getType() == iotaType)
            return (T) iota;
        else if (iotaType == HexIotaTypes.DOUBLE
                && iota.getType() == FiveDimCastingIotaTypes.QUATERNION
                && ((QuaternionIota) iota).isReal())
            return (T) new DoubleIota(((QuaternionIota) iota).getX0());
        throw new IllegalStateException("Attempting to downcast " + iota + " to type: " + iotaType);
    }
}
