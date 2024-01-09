package net.masterbw3.fivedimcasting.mixin;

import at.petrak.hexcasting.api.casting.SpellList;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import at.petrak.hexcasting.api.casting.mishaps.MishapOthersName;
import net.masterbw3.fivedimcasting.api.casting.iota.CellIota;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

@Mixin(MishapOthersName.Companion.class)
public class MixinCellTrueNameProtection {
    @ModifyVariable(method = "getTrueNameFromDatum", at = @At("HEAD"), ordinal = 0)
    private Iota injected(Iota x) {
        if (x instanceof CellIota c) {
            List<Iota> list = new ArrayList<>();
            list.add(c.getStoredIota());
            return new ListIota(new SpellList.LList(list));
        } else return x;
    }
}
