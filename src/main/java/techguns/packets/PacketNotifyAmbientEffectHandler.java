package techguns.packets;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.*;
import techguns.api.npc.INPCTechgunsShooter;
import techguns.api.render.IItemRenderer;
import techguns.capabilities.TGExtendedPlayerClient;
import techguns.capabilities.TGShooterValues;
import techguns.client.particle.TGFX;
import techguns.client.particle.TGParticleSystem;
import techguns.client.render.ItemRenderHack;
import techguns.client.render.item.RenderItemBase;
import techguns.items.guns.GenericGun;

public class PacketNotifyAmbientEffectHandler extends HandlerTemplate<PacketNotifyAmbientEffectChange> {

    @Override
    protected void handle(PacketNotifyAmbientEffectChange message, MessageContext ctx) {
        Entity ent = TGPackets.getPlayerFromContext(ctx).world.getEntityByID(message.entityId);
        if (ent instanceof EntityLivingBase elb) {
            ItemStack stack = elb.getHeldItem(message.hand);

            if (!stack.isEmpty() && stack.getItem() instanceof GenericGun) {

                if (((GenericGun) stack.getItem()).getAmmoLeft(stack) > 0) {
                    IItemRenderer renderer = ItemRenderHack.getRendererForItem(stack.getItem());
                    if (renderer instanceof RenderItemBase itemRenderer) {

                        String fxlist = itemRenderer.getAmbientParticleFX();
                        if (fxlist != null) {
                            List<TGParticleSystem> systems = TGFX.createFXOnEntityItemAttached(elb, message.hand, fxlist);

                            if (elb instanceof EntityPlayer) {
                                TGExtendedPlayerClient props = TGExtendedPlayerClient.get((EntityPlayer) elb);
                                props.addSystemsHand(message.hand, systems);
                            } else if (elb instanceof INPCTechgunsShooter) {
                                TGShooterValues props = TGShooterValues.get(elb);
                                props.addSystemsHand(message.hand, systems);
                            }
                        } else {
                            Techguns.proxy.clearItemParticleSystemsHand(elb, message.hand);
                        }
                    } else {
                        Techguns.proxy.clearItemParticleSystemsHand(elb, message.hand);
                    }
                } else {
                    Techguns.proxy.clearItemParticleSystemsHand(elb, message.hand);
                }
            } else {
                Techguns.proxy.clearItemParticleSystemsHand(elb, message.hand);
            }

        }
    }

}
