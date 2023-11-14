package net.masterbw3.fivedimcasting.mixin;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.SpellList;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi;
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota;
import net.masterbw3.fivedimcasting.mixinImpl.MixinGettersImplKt;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(OperatorUtils.class)
public abstract class MixinGetters {
    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final double getDouble(@NotNull List $this$getDouble, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        FiveDimCastingApi.LOGGER.info("OWO FR2");
        return MixinGettersImplKt.replaceGetDouble($this$getDouble, idx, argc);
    }
}