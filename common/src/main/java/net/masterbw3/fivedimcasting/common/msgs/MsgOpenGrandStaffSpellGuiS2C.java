package net.masterbw3.fivedimcasting.common.msgs;

import at.petrak.hexcasting.api.casting.eval.ResolvedPattern;
import at.petrak.hexcasting.client.gui.GuiSpellcasting;
import at.petrak.hexcasting.common.msgs.IMessage;
import io.netty.buffer.ByteBuf;
import java.util.List;

import net.masterbw3.fivedimcasting.FiveDimCasting;
import net.masterbw3.fivedimcasting.mixinImpl.IMixinGuiSpellCasting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

import static net.masterbw3.fivedimcasting.api.FiveDimCastingApi.modLoc;

//https://github.com/FallingColors/HexMod/blob/main/Common/src/main/java/at/petrak/hexcasting/common/msgs/MsgOpenSpellGuiS2C.java
public record MsgOpenGrandStaffSpellGuiS2C(Hand hand, List<ResolvedPattern> patterns,
                                 List<NbtCompound> stack,
                                 NbtCompound ravenmind,
                                 int parenCount
)
        implements IMessage {
    public static final Identifier ID = modLoc("gcgui");

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    public static MsgOpenGrandStaffSpellGuiS2C deserialize(ByteBuf buffer) {

        var buf = new PacketByteBuf(buffer);

        var hand = buf.readEnumConstant(Hand.class);

        var patterns = buf.readList(fbb -> ResolvedPattern.fromNBT(fbb.readUnlimitedNbt()));

        var stack = buf.readList(PacketByteBuf::readNbt);
        var raven = buf.readUnlimitedNbt();

        var parenCount = buf.readVarInt();

        return new MsgOpenGrandStaffSpellGuiS2C(hand, patterns, stack, raven, parenCount);
    }

    public void serialize(PacketByteBuf buf) {

        buf.writeEnumConstant(this.hand);

        buf.writeCollection(this.patterns, (fbb, pat) -> fbb.writeNbt(pat.serializeToNBT()));

        buf.writeCollection(this.stack, PacketByteBuf::writeNbt);
        buf.writeNbt(this.ravenmind);

        buf.writeVarInt(this.parenCount);
    }

    public static void handle(MsgOpenGrandStaffSpellGuiS2C msg) {

        MinecraftClient.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                var mc = MinecraftClient.getInstance();
                var grandStaffGui = new GuiSpellcasting(msg.hand(), msg.patterns(), msg.stack, msg.ravenmind,
                        msg.parenCount);
                ((IMixinGuiSpellCasting) (Object) grandStaffGui).fivedimcasting$enableGrandStaffCastingMode();
                mc.setScreen(
                        grandStaffGui);
            }
        });
    }
}

