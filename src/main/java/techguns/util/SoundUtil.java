package techguns.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import techguns.TGPackets;
import techguns.*;
import techguns.client.ClientProxy;
import techguns.client.audio.TGSoundCategory;
import techguns.packets.PacketPlaySound;

public class SoundUtil {

    /**
     * Plays a moving Sound on the specified Entity, in front of the entity.
     */
    public static void playSoundOnEntityGunPosition(World world, Entity entity, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving, boolean playOnOwnPlayer, TGSoundCategory category, EntityCondition condition) {
        if (!world.isRemote) {
            TGPackets.wrapper.sendToAllAround(new PacketPlaySound(soundname, entity, volume, pitch, repeat, moving, true, playOnOwnPlayer, category, condition),
                    new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 100.0f));
        } else {
            Techguns.proxy.playSoundOnEntity(entity, soundname, volume, pitch, repeat, moving, true, true, category, condition);
        }
    }


    /**
     * Plays a moving Sound on the specified Entity, in front of the entity.
     */
    public static void playSoundOnEntityGunPosition(World world, Entity entity, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving, boolean playOnOwnPlayer, TGSoundCategory category) {
        if (!world.isRemote) {
            TGPackets.wrapper.sendToAllAround(new PacketPlaySound(soundname, entity, volume, pitch, repeat, moving, true, playOnOwnPlayer, category),
                    new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 100.0f));
        } else {
            Techguns.proxy.playSoundOnEntity(entity, soundname, volume, pitch, repeat, moving, true, true, category);
        }
    }

    /**
     * Plays a moving Sound on the specified Entity, in front of the entity.
     */
    public static void playSoundOnEntityGunPosition(World world, Entity entity, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving, TGSoundCategory category) {
        playSoundOnEntityGunPosition(world, entity, soundname, volume, pitch, repeat, moving, false, category);
    }

    /**
     * Like playSoundOnEntityGunPosition() but does a check for last played own reload.
     */
    public static void playReloadSoundOnEntity(World world, Entity entity, SoundEvent soundname, float volume, float pitch, boolean repeat, boolean moving, TGSoundCategory category) {
        if (!world.isRemote) {
            TGPackets.wrapper.sendToAllAround(new PacketPlaySound(soundname, entity, volume, pitch, repeat, moving, true, category),
                    new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 100.0f));
        } else {
            ClientProxy cp = ClientProxy.get();
            if (cp.lastReloadsoundPlayed - System.currentTimeMillis() < -500) {
                cp.lastReloadsoundPlayed = System.currentTimeMillis();
                Techguns.proxy.playSoundOnEntity(entity, soundname, volume, pitch, repeat, moving, true, category);
            }
        }
    }


    /**
     * Plays a non-moving Sound at the specified Entity's position
     */
    public static void playSoundAtEntityPos(World world, Entity entity, SoundEvent soundname, float volume, float pitch, boolean repeat, TGSoundCategory category) {
        if (!world.isRemote) {
            TGPackets.wrapper.sendToAllAround(new PacketPlaySound(soundname, entity, volume, pitch, repeat, false, category),
                    new TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 100.0f));
        } else {
            Techguns.proxy.playSoundOnEntity(entity, soundname, volume, pitch, repeat, false, false, category);
        }
    }

}
