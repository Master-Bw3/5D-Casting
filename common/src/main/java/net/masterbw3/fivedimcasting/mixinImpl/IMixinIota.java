package net.masterbw3.fivedimcasting.mixinImpl;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;

import java.util.Optional;

public interface IMixinIota {
    public <T extends Iota> Optional<T> tryCastTo(IotaType<T> iotaType);
}
