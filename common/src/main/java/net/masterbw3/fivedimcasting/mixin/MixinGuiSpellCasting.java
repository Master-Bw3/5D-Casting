package net.masterbw3.fivedimcasting.mixin;

import at.petrak.hexcasting.client.gui.GuiSpellcasting;
import net.masterbw3.fivedimcasting.FiveDimCasting;
import net.masterbw3.fivedimcasting.mixinImpl.IMixinGuiSpellCasting;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiSpellcasting.class)
public abstract class MixinGuiSpellCasting implements IMixinGuiSpellCasting {

    @Unique
    private boolean fivedimcasting$grandStaffCasting = false;

    @Override
    public void fivedimcasting$setGrandStaffCasting() {
        fivedimcasting$grandStaffCasting = true;
    }

    @Override
    public boolean fivedimcasting$isGrandStaffCasting() {
        return fivedimcasting$grandStaffCasting;
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void mixinRender(DrawContext graphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        FiveDimCasting.LOGGER.info("mixin time");
        ci.cancel();
    }

}
