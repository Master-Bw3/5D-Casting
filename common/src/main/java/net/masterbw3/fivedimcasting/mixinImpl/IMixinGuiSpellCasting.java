package net.masterbw3.fivedimcasting.mixinImpl;

import at.petrak.hexcasting.api.casting.eval.ResolvedPattern;
import at.petrak.hexcasting.api.casting.math.HexCoord;
import at.petrak.hexcasting.client.sound.GridSoundInstance;
import net.minecraft.util.math.Vec2f;

import java.util.HashSet;
import java.util.List;

public interface IMixinGuiSpellCasting {
    void fivedimcasting$enableGrandStaffCastingMode();

    boolean fivedimcasting$isGrandStaffCasting();

//    GridSoundInstance getAmbianceSoundInstance();
//
//    List<ResolvedPattern> getPatterns();
//
//    HashSet<HexCoord> getUsedSpots();

}
