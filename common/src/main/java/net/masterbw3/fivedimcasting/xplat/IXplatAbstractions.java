package net.masterbw3.fivedimcasting.xplat;

import at.petrak.hexcasting.api.casting.ActionRegistryEntry;
import net.minecraft.registry.Registry;

public interface IXplatAbstractions {
    Registry<ActionRegistryEntry> getActionRegistry();

}
