package net.masterbw3.fivedimcasting.mixin;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.SpellList;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs;
import com.mojang.datafixers.util.Either;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.masterbw3.fivedimcasting.api.FiveDimCastingApi;
import net.masterbw3.fivedimcasting.api.casting.iota.QuaternionIota;
import net.masterbw3.fivedimcasting.mixinImpl.MixinGettersImplKt;
import net.minecraft.util.math.Vec3d;
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
    public static final double getDouble(@NotNull List<Iota> $this$getDouble, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.replaceGetDouble($this$getDouble, idx, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final double getPositiveDouble(@NotNull List<Iota> $this$getPositiveDouble, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.replaceGetPositiveDouble($this$getPositiveDouble, idx, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final double getPositiveDoubleUnder(@NotNull List<Iota> $this$getPositiveDoubleUnder, int idx, double max, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.replaceGetPositiveDoubleUnder($this$getPositiveDoubleUnder, idx, max, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final double getPositiveDoubleUnderInclusive(@NotNull List<Iota> $this$getPositiveDoubleUnderInclusive, int idx, double max, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.replaceGetPositiveDoubleUnderInclusive($this$getPositiveDoubleUnderInclusive, idx, max, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final double getDoubleBetween(@NotNull List<Iota> $this$getDoubleBetween, int idx, double min, double max, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.replaceGetDoubleBetween($this$getDoubleBetween, idx, min, max, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final int getInt(@NotNull List<Iota> $this$getInt, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.replaceGetInt($this$getInt, idx, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final long getLong(@NotNull List<Iota> $this$getLong, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.replaceGetLong($this$getLong, idx, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final int getPositiveInt(@NotNull List<Iota> $this$getPositiveInt, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.replaceGetPositiveInt($this$getPositiveInt, idx, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final int getPositiveIntUnder(@NotNull List<Iota> $this$getPositiveIntUnder, int idx, int max, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.replaceGetPositiveIntUnder($this$getPositiveIntUnder, idx, max, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final int getPositiveIntUnderInclusive(@NotNull List<Iota> $this$getPositiveIntUnderInclusive, int idx, int max, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.replaceGetPositiveIntUnderInclusive($this$getPositiveIntUnderInclusive, idx, max, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final int getIntBetween(@NotNull List<Iota> $this$getIntBetween, int idx, int min, int max, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.replaceGetIntBetween($this$getIntBetween, idx, min, max, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final @NotNull Either<Double, Vec3d> getNumOrVec(@NotNull List<Iota> $this$getNumOrVec, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.replaceGetNumOrVec($this$getNumOrVec, idx, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final @NotNull Either<Long, SpellList> getLongOrList(@NotNull List<Iota> $this$getLongOrList, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.replaceGetLongOrList($this$getLongOrList, idx, argc);
    }


}