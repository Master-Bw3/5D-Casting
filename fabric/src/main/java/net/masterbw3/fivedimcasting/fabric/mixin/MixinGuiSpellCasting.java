package net.masterbw3.fivedimcasting.fabric.mixin;

import at.petrak.hexcasting.api.casting.math.HexCoord;
import at.petrak.hexcasting.client.gui.GuiSpellcasting;
import at.petrak.hexcasting.client.render.RenderLib;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.masterbw3.fivedimcasting.FiveDimCasting;
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

import java.util.ArrayList;
import java.util.Set;

import static at.petrak.hexcasting.client.render.RenderLib.screenCol;

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

    //set mouse coord to be center of screen
    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 0)
    public HexCoord modifyMouseCoord(HexCoord mouseCoord) {
        if (fivedimcasting$isGrandStaffCasting()) {
            var $this = ((GuiSpellcasting)(Object)this);
            return $this.pxToCoord(new Vec2f((float) $this.width / 2, (float) $this.height / 2));
        } else {
            return mouseCoord;
        }
    }

    //offset by center instead of mouse coords
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

    //change radius
    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 2)
    public int modifyRadius(int radius) {
        if (fivedimcasting$isGrandStaffCasting()) {
            return 5;
        } else {
            return radius;
        }
    }

    //make all visible dots with a size > 0.1 have a size of 1
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"))
    private float modifyDotSize(float value, float min, float max, Operation<Float> original) {
        if (fivedimcasting$isGrandStaffCasting()) {
            return (float) Math.ceil(original.call(value, min, max) - 0.1);
        } else {
            return original.call(value, min, max);
        }
    }

//    //make unused dots outlined with no fill
//    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lat/petrak/hexcasting/client/render/RenderLib;drawSpot(Lorg/joml/Matrix4f;Lnet/minecraft/util/math/Vec2f;FFFFF)V"))
//    private void modifyDotStyle(Matrix4f mat, Vec2f point, float radius, float r, float g, float b, float a, Operation<Void> original) {
//        if (fivedimcasting$isGrandStaffCasting()) {
//            RenderSystem.defaultBlendFunc();
//            FiveDimCastingUtilsKt.drawSpotOutline(mat, point, radius, 0xFFFECBE6);
//        } else {
//            original.call(mat, point, radius, r, g, b, a);
//        }
//    }

    @WrapOperation(method = "mouseClicked", at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"))
    private boolean mixinValidPointClicked(Set<HexCoord> usedSpots, Object coord, Operation<Boolean> original) {
        if (fivedimcasting$isGrandStaffCasting()) {
            var $this = ((GuiSpellcasting) (Object) this);
            var center = new Vec2f((float) $this.width / 2, (float) $this.height / 2).negate();
            var delta = $this.coordToPx((HexCoord) coord).add(center).length();
            var pointIsRendered = Math.ceil(Math.min(1f, (1.0f - ((delta - $this.hexSize()) / (5f * $this.hexSize())) - 0.1))) == 1;
            return original.call(usedSpots, coord) || !pointIsRendered;
        } else {
            return original.call(usedSpots, coord);
        }
    }

    @WrapOperation(method = "mouseDragged", at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"))
    private boolean mixinValidPointDragged(Set<HexCoord> usedSpots, Object coord, Operation<Boolean> original) {
        if (fivedimcasting$isGrandStaffCasting()) {
            var $this = ((GuiSpellcasting) (Object) this);
            var center = new Vec2f((float) $this.width / 2, (float) $this.height / 2).negate();
            var delta = $this.coordToPx((HexCoord) coord).add(center).length();
            var pointIsRendered = Math.ceil(Math.min(1f, (1.0f - ((delta - $this.hexSize()) / (5f * $this.hexSize())) - 0.1))) == 1;
            FiveDimCasting.LOGGER.info(pointIsRendered);
            return original.call(usedSpots, coord) || !pointIsRendered;
        } else {
            return original.call(usedSpots, coord);
        }
    }


    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;defaultBlendFunc()V", shift = At.Shift.AFTER))
    public void mixinRenderDrawLines(DrawContext graphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        if (fivedimcasting$isGrandStaffCasting()) {
            var $this = ((GuiSpellcasting) (Object) this);
            var mat = graphics.getMatrices().peek().getPositionMatrix();

            //settings
            final float line_width = 1;
            final int color = screenCol(0xff_64c8ff);
            float y_offset = (float) (5 * $this.width) / $this.height;
            float x_offset = (float) (y_offset * (2.0 / Math.sqrt(3.0)) / 2) ;

            //center hexagon dimensions
            var hex_top_y = $this.coordToPx(new HexCoord(0, -3)).y;
            var hex_top_left_x = $this.coordToPx(new HexCoord(0, -3)).x;
            var hex_top_right_x = $this.coordToPx(new HexCoord(3, -3)).x;

            var hex_middle_y = $this.coordToPx(new HexCoord(0, 0)).y;
            var hex_middle_left_x = $this.coordToPx(new HexCoord(-3, 0)).x;
            var hex_middle_right_x = $this.coordToPx(new HexCoord(3, 0)).x;


            var hex_bottom_y = $this.coordToPx(new HexCoord(0, 3)).y;
            var hex_bottom_left_x = $this.coordToPx(new HexCoord(-3, 3)).x;
            var hex_bottom_right_x = $this.coordToPx(new HexCoord(0, 3)).x;

            //draw bottom center hexagon outline
            var bottomCenterOutlinePoints = new ArrayList<Vec2f>();
            var bottom_left = new Vec2f(hex_bottom_left_x - x_offset, hex_bottom_y + y_offset);
            var bottom_right = new Vec2f(hex_bottom_right_x + x_offset, hex_bottom_y + y_offset);
            var middle_right = new Vec2f(hex_middle_right_x + x_offset * 2, hex_middle_y);

            bottomCenterOutlinePoints.add(bottom_left);
            bottomCenterOutlinePoints.add(bottom_right);
            bottomCenterOutlinePoints.add(middle_right);

            RenderLib.drawLineSeq(mat, bottomCenterOutlinePoints, line_width,0, color, color);

            //draw top center hexagon outline
            //draw bottom center hexagon outline
            var topCenterOutlinePoints = new ArrayList<Vec2f>();
            var top_right = new Vec2f(hex_top_right_x + x_offset, hex_top_y - y_offset);
            var top_left = new Vec2f(hex_top_left_x - x_offset, hex_top_y - y_offset);
            var middle_left = new Vec2f(hex_middle_left_x - x_offset * 2, hex_middle_y);

            topCenterOutlinePoints.add(top_right);
            topCenterOutlinePoints.add(top_left);
            topCenterOutlinePoints.add(middle_left);

            RenderLib.drawLineSeq(mat, topCenterOutlinePoints, line_width,0, color, color);
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
