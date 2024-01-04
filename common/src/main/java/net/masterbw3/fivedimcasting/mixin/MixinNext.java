package net.masterbw3.fivedimcasting.mixin;

import at.petrak.hexcasting.common.casting.arithmetic.operator.OperatorUtilsKt;
import net.masterbw3.fivedimcasting.mixinImpl.MixinNextImplKt;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Iterator;

@Mixin(OperatorUtilsKt.class)
public class MixinNext {

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final double nextDouble(@NotNull Iterator $this$nextDouble, int argc) {
        return MixinNextImplKt.nextDouble($this$nextDouble, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final int nextInt(@NotNull Iterator $this$nextInt, int argc) {
        return MixinNextImplKt.nextInt($this$nextInt, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final int nextPositiveIntUnder(@NotNull Iterator $this$nextPositiveIntUnder, int max, int argc) {
        return MixinNextImplKt.nextPositiveIntUnder($this$nextPositiveIntUnder, max, argc);
    }

    /**
     * @author Master_Bw3
     * @reason I wanted to
     */
    @Overwrite(remap = false)
    public static final int nextPositiveIntUnderInclusive(@NotNull Iterator $this$nextPositiveIntUnderInclusive, int max, int argc) {
        return MixinNextImplKt.nextPositiveIntUnderInclusive($this$nextPositiveIntUnderInclusive, max, argc);
    }
}