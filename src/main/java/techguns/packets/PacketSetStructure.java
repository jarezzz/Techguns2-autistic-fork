package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.items.WorldGenTestTool;

public class PacketSetStructure implements IMessage {

    private String structureName;

    public PacketSetStructure() {}

    public PacketSetStructure(String structureName) {
        this.structureName = structureName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.structureName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, structureName);
    }

    public static class Handler implements IMessageHandler<PacketSetStructure, IMessage> {

        @Override
        public IMessage onMessage(PacketSetStructure message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;

            player.getServerWorld().addScheduledTask(() -> {
                ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);

                if (!(heldItem.getItem() instanceof WorldGenTestTool)) {
                    heldItem = player.getHeldItem(EnumHand.OFF_HAND);
                }

                if (heldItem.getItem() instanceof WorldGenTestTool) {
                    if (!heldItem.hasTagCompound()) {
                        heldItem.setTagCompound(new NBTTagCompound());
                    }
                    heldItem.getTagCompound().setString("structure", message.structureName);
                }
            });

            return null;
        }
    }
}