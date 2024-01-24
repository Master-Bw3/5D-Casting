package net.masterbw3.fivedimcasting.common.items;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.item.IotaHolderItem;
import at.petrak.hexcasting.api.item.MediaHolderItem;
import at.petrak.hexcasting.api.utils.NBTHelper;
import at.petrak.hexcasting.common.items.ItemStaff;
import at.petrak.hexcasting.common.lib.HexSounds;
import at.petrak.hexcasting.common.msgs.MsgClearSpiralPatternsS2C;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import net.masterbw3.fivedimcasting.common.msgs.MsgOpenGrandStaffSpellGuiS2C;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ItemGrandStaff extends ItemStaff implements IotaHolderItem {

    public static final String TAG_HEX = "hex";
    public static final String TAG_STACK = "stack";
    public static final String TAG_RAVENMIND = "ravenmind";



    public ItemGrandStaff(Settings pProperties) {
        super(pProperties);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (player.isSneaking()) {
            if (world.isClient()) {
                player.playSound(HexSounds.STAFF_RESET, 1f, 1f);
            } else if (player instanceof ServerPlayerEntity serverPlayer) {
                IXplatAbstractions.INSTANCE.clearCastingData(serverPlayer);
                var packet = new MsgClearSpiralPatternsS2C(player.getUuid());
                IXplatAbstractions.INSTANCE.sendPacketToPlayer(serverPlayer, packet);
                IXplatAbstractions.INSTANCE.sendPacketTracking(serverPlayer, packet);
            }
        }

        if (!world.isClient() && player instanceof ServerPlayerEntity serverPlayer) {
            var harness = IXplatAbstractions.INSTANCE.getStaffcastVM(serverPlayer, hand);
            var patterns = IXplatAbstractions.INSTANCE.getPatternsSavedInUi(serverPlayer);
            var descs = harness.generateDescs();

            IXplatAbstractions.INSTANCE.sendPacketToPlayer(serverPlayer,
                    new MsgOpenGrandStaffSpellGuiS2C(hand, patterns, descs.getFirst(), descs.getSecond(),
                            0)); // TODO: Fix!
        }

        player.incrementStat(Stats.USED.getOrCreateStat(this));

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    @Override
    public @Nullable NbtCompound readIotaTag(ItemStack stack) {
        return null;
    }

    @Override
    public boolean canWrite(ItemStack stack, @Nullable Iota iota) {
        return false;
    }

    @Override
    public void writeDatum(ItemStack stack, @Nullable Iota datum) {
        writeDatum(stack, datum, TAG_HEX);
    }

    public void writeDatum(ItemStack stack,  @Nullable Iota datum, String tag) {
        if (datum == null) {
            stack.removeSubNbt(tag);
        } else {
            NBTHelper.put(stack, tag, IotaType.serialize(datum));
        }
    }
}
