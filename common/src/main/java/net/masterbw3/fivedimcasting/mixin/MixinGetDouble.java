package net.masterbw3.fivedimcasting.mixin;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;;
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi;
import net.masterbw3.fivedimcasting.mixinImpl.DoubleCompatibilityMixins;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;

@Mixin(OperatorUtils.class)
public abstract class MixinGetDouble {
    @Inject(
            method = "getDouble",
            at = @At("HEAD"),
            remap = false
    )
    private static void fivedimcasting$forceInitIfFabric(List<? extends Iota> $this$getDouble, int idx, int argc, CallbackInfoReturnable<Double> cir) {
        FiveDimCastingApi.LOGGER.info("5D Mixin!");




    }
}