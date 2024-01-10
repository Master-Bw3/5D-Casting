package net.masterbw3.fivedimcasting.fabric.mixin;

import at.petrak.hexcasting.api.casting.math.HexCoord;
import at.petrak.hexcasting.client.gui.GuiSpellcasting;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.masterbw3.fivedimcasting.mixinImpl.IMixinGuiSpellCasting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiSpellcasting.class)
public abstract class MixinGuiSpellCasting extends Screen implements IMixinGuiSpellCasting {

    @Unique
    private boolean fivedimcasting$grandStaffCasting = false;

    protected MixinGuiSpellCasting(Text title) {
        super(title);
    }

    @Override
    public void fivedimcasting$enableGrandStaffCastingMode() {
        fivedimcasting$grandStaffCasting = true;
    }

    @Override
    public boolean fivedimcasting$isGrandStaffCasting() {
        return fivedimcasting$grandStaffCasting;
    }

    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 0)
    public HexCoord modifyMousePos(HexCoord mouseCoord) {
        if (fivedimcasting$isGrandStaffCasting()) {
            var $this = ((GuiSpellcasting)(Object)this);
            return $this.pxToCoord(new Vec2f((float) $this.width / 2, (float) $this.height / 2));
        } else {
            return mouseCoord;
        }
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec2f;add(Lnet/minecraft/util/math/Vec2f;)Lnet/minecraft/util/math/Vec2f;"))
    private Vec2f test(Vec2f instance, Vec2f vec, Operation<Vec2f> original) {
        if (fivedimcasting$isGrandStaffCasting()) {
            var $this = ((GuiSpellcasting) (Object) this);
            var center = new Vec2f((float) $this.width / 2, (float) $this.height / 2).negate();
            return original.call(instance, center);
        } else {
            return original.call(instance, vec);
        }
    }

    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 2)
    public int modifyRadius(int radius) {
        if (fivedimcasting$isGrandStaffCasting()) {
            return 5;
        } else {
            return radius;
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    public void mixinRenderHead(DrawContext graphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        var $this = ((GuiSpellcasting) (Object) this);
        if (fivedimcasting$isGrandStaffCasting()) {

        }
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"))
    private float test(float value, float min, float max, Operation<Float> original) {
        if (fivedimcasting$isGrandStaffCasting()) {
            return (float) Math.ceil(original.call(value, min, max));
        } else {
            return original.call(value, min, max);
        }
    }

//    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lat/petrak/hexcasting/api/casting/math/HexPattern;toLines(FLnet/minecraft/util/math/Vec2f;)Ljava/util/List;"))
//    private List<Vec2f> test(HexPattern pattern, float hexSize, Vec2f coordsPx, Operation<List<Vec2f>> original) {
//        if (fivedimcasting$isGrandStaffCasting()) {
//            var $this = ((GuiSpellcasting) (Object) this);
//            var center = new Vec2f((float) $this.width / 2, (float) $this.height / 2).negate();
//            return FiveDimCastingUtilsKt.hexPatternToFlatCoordLines(pattern, hexSize, coordsPx);
//        } else {
//            return original.call(pattern, hexSize, coordsPx);
//        }
//    }

//    @Inject(method = "coordToPx", at = @At("HEAD"), cancellable = true)
//    public void flatCoordToPx(HexCoord coord, CallbackInfoReturnable<Vec2f> cir) {
//        if (fivedimcasting$isGrandStaffCasting()) {
//            var $this = ((GuiSpellcasting) (Object) this);
//            cir.setReturnValue(FiveDimCastingUtilsKt.squareCoordToPx(coord, $this.hexSize(), $this.coordsOffset()));
//        }
//    }
//
//    @Inject(method = "pxToCoord", at = @At("HEAD"), cancellable = true)
//    public void pxToFlatCoord(Vec2f px, CallbackInfoReturnable<HexCoord> cir) {
//        if (fivedimcasting$isGrandStaffCasting()) {
//            var $this = ((GuiSpellcasting) (Object) this);
//            cir.setReturnValue(FiveDimCastingUtilsKt.pxToSquareCoord(px, $this.hexSize(), $this.coordsOffset()));
//        }
//    }


}
