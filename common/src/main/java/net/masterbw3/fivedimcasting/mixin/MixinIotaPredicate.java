package net.masterbw3.fivedimcasting.mixin;


import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.iota.BooleanIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes;
import net.masterbw3.fivedimcasting.FiveDimCasting;
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi;
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota;
import net.masterbw3.fivedimcasting.lib.hex.FiveDimCastingIotaTypes;
import net.masterbw3.fivedimcasting.mixinImpl.IMixinIota;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(IotaPredicate.OfType.class)
public class MixinIotaPredicate implements IotaPredicate {
    @Shadow(remap = false)
    @Final
    private IotaType<?> type;

    /**
     * @author Master_Bw3
     * @reason testing something
     */
    @Overwrite(remap = false)
    public boolean test(Iota iota) {
        FiveDimCasting.LOGGER.info("testing");
        return ((IMixinIota) (Object) iota).tryCastTo(this.type).isPresent();
    }
}