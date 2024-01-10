package net.masterbw3.fivedimcasting.fabric.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.masterbw3.fivedimcasting.common.msgs.MsgOpenGrandStaffSpellGuiS2C;
import net.minecraft.network.PacketByteBuf;

import java.util.function.Consumer;
import java.util.function.Function;


public class FabricPacketHandler {
    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(MsgOpenGrandStaffSpellGuiS2C.ID,
                makeClientBoundHandler(MsgOpenGrandStaffSpellGuiS2C::deserialize, MsgOpenGrandStaffSpellGuiS2C::handle));

    }

    private static <T> ClientPlayNetworking.PlayChannelHandler makeClientBoundHandler(
            Function<PacketByteBuf, T> decoder, Consumer<T> handler) {
        return (_client, _handler, buf, _responseSender) -> handler.accept(decoder.apply(buf));
    }
}
