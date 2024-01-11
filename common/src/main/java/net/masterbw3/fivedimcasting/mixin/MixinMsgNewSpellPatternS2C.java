package net.masterbw3.fivedimcasting.mixin;

import at.petrak.hexcasting.client.gui.GuiSpellcasting;
import at.petrak.hexcasting.common.msgs.MsgNewSpellPatternS2C;
import net.masterbw3.fivedimcasting.FiveDimCasting;
import net.masterbw3.fivedimcasting.client.gui.GuiGrandStaffSpellCasting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MsgNewSpellPatternS2C.class)
public abstract class MixinMsgNewSpellPatternS2C {

    @Inject(method = "handle", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/MinecraftClient;getInstance()Lnet/minecraft/client/MinecraftClient;"))
    private static void test(MsgNewSpellPatternS2C self, CallbackInfo ci) {
        FiveDimCasting.LOGGER.info("hi");
        var screen = MinecraftClient.getInstance().currentScreen;
        if (screen instanceof GuiGrandStaffSpellCasting spellGui) {
            spellGui.recvServerUpdate(self.info(), self.index());
        }
    }
}
