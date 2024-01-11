package net.masterbw3.fivedimcasting.mixin;

import at.petrak.hexcasting.api.casting.OperatorUtils;
import at.petrak.hexcasting.api.casting.SpellList;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs;
import com.mojang.datafixers.util.Either;
import net.masterbw3.fivedimcasting.mixinImpl.MixinGettersImplKt;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import java.util.List;

@Mixin(OperatorUtils.class)
public abstract class MixinGetters {

    @Overwrite(remap = false)
    public static final double getDouble(@NotNull List<Iota> $this$getDouble, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.getDouble($this$getDouble, idx, argc);
    }


    @Overwrite(remap = false)
    public static final double getPositiveDouble(@NotNull List<Iota> $this$getPositiveDouble, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.getPositiveDouble($this$getPositiveDouble, idx, argc);
    }


    @Overwrite(remap = false)
    public static final double getPositiveDoubleUnder(@NotNull List<Iota> $this$getPositiveDoubleUnder, int idx, double max, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.getPositiveDoubleUnder($this$getPositiveDoubleUnder, idx, max, argc);
    }


    @Overwrite(remap = false)
    public static final double getPositiveDoubleUnderInclusive(@NotNull List<Iota> $this$getPositiveDoubleUnderInclusive, int idx, double max, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.getPositiveDoubleUnderInclusive($this$getPositiveDoubleUnderInclusive, idx, max, argc);
    }


    @Overwrite(remap = false)
    public static final double getDoubleBetween(@NotNull List<Iota> $this$getDoubleBetween, int idx, double min, double max, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.getDoubleBetween($this$getDoubleBetween, idx, min, max, argc);
    }


    @Overwrite(remap = false)
    public static final int getInt(@NotNull List<Iota> $this$getInt, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.getInt($this$getInt, idx, argc);
    }


    @Overwrite(remap = false)
    public static final long getLong(@NotNull List<Iota> $this$getLong, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.getLong($this$getLong, idx, argc);
    }


    @Overwrite(remap = false)
    public static final int getPositiveInt(@NotNull List<Iota> $this$getPositiveInt, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.getPositiveInt($this$getPositiveInt, idx, argc);
    }


    @Overwrite(remap = false)
    public static final int getPositiveIntUnder(@NotNull List<Iota> $this$getPositiveIntUnder, int idx, int max, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.getPositiveIntUnder($this$getPositiveIntUnder, idx, max, argc);
    }


    @Overwrite(remap = false)
    public static final int getPositiveIntUnderInclusive(@NotNull List<Iota> $this$getPositiveIntUnderInclusive, int idx, int max, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.getPositiveIntUnderInclusive($this$getPositiveIntUnderInclusive, idx, max, argc);
    }


    @Overwrite(remap = false)
    public static final int getIntBetween(@NotNull List<Iota> $this$getIntBetween, int idx, int min, int max, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.getIntBetween($this$getIntBetween, idx, min, max, argc);
    }


    @Overwrite(remap = false)
    public static final @NotNull Either<Double, Vec3d> getNumOrVec(@NotNull List<Iota> $this$getNumOrVec, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.getNumOrVec($this$getNumOrVec, idx, argc);
    }


    @Overwrite(remap = false)
    public static final @NotNull Either<Long, SpellList> getLongOrList(@NotNull List<Iota> $this$getLongOrList, int idx, int argc) throws MishapInvalidIota, MishapNotEnoughArgs {
        return MixinGettersImplKt.getLongOrList($this$getLongOrList, idx, argc);
    }


}