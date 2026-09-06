package techguns.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import techguns.TGPackets;
import techguns.TGSounds;
import techguns.*;
import techguns.util.DataUtil;

import java.util.BitSet;

public class PacketGunImpactFX implements IMessage {

	short soundType;
	double loc_x;
	double loc_y;
	double loc_z;
	float pitch;
	float yaw;

	boolean incendiary=false;

	public PacketGunImpactFX(short soundType, double loc_x, double loc_y, double loc_z, float pitch, float yaw, boolean inc) {
		this.soundType = soundType;
		this.loc_x = loc_x;
		this.loc_y = loc_y;
		this.loc_z = loc_z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.incendiary=inc;
	}
	
	//clientside constructor
	public PacketGunImpactFX() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.soundType=buf.readShort();
		this.loc_x=buf.readDouble();
		this.loc_y=buf.readDouble();
		this.loc_z=buf.readDouble();
		this.pitch=buf.readFloat();
		this.yaw = buf.readFloat();
		byte flags1 = buf.readByte();
		BitSet bs = DataUtil.uncompress(flags1);
		this.incendiary = bs.get(0);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeShort(soundType);
		buf.writeDouble(loc_x);
		buf.writeDouble(loc_y);
		buf.writeDouble(loc_z);
		buf.writeFloat(pitch);
		buf.writeFloat(yaw);
		byte flags1=DataUtil.compress(incendiary);
		buf.writeByte(flags1);
	}

	public static class Handler extends HandlerTemplate<PacketGunImpactFX> {
		
		@Override
		protected void handle(PacketGunImpactFX m, MessageContext ctx) {
			EntityPlayer ply = TGPackets.getPlayerFromContext(ctx);
			double x,y,z;
			x=m.loc_x;
			y=m.loc_y;
			z=m.loc_z;
			World world = ply.world;

			if(m.incendiary) {
				Techguns.proxy.createFX("Impact_IncendiaryBullet", world, x, y, z, 0.0D, 0.0D, 0.0D, m.pitch, m.yaw);
			}
			
			if(m.soundType==0) {
				world.playSound(x, y, z, TGSounds.BULLET_IMPACT_STONE, SoundCategory.AMBIENT, 1.0f, 1.0f, true);
				Techguns.proxy.createFX("Impact_BulletRock", world, x, y, z, 0.0D, 0.0D, 0.0D, m.pitch, m.yaw);

			} else if(m.soundType==1) {
				world.playSound(x, y, z, TGSounds.BULLET_IMPACT_WOOD, SoundCategory.AMBIENT, 1.0f, 1.0f, true);
				Techguns.proxy.createFX("Impact_BulletWood", world, x, y, z, 0.0D, 0.0D, 0.0D, m.pitch, m.yaw);
				
			} else if(m.soundType==2) {
				world.playSound(x, y, z, TGSounds.BULLET_IMPACT_GLASS, SoundCategory.AMBIENT, 1.0f, 1.0f, true);
				Techguns.proxy.createFX("Impact_BulletGlass", world, x, y, z, 0.0D, 0.0D, 0.0D, m.pitch, m.yaw);
				
			} else if(m.soundType==3) {
				world.playSound(x, y, z, TGSounds.BULLET_IMPACT_METAL, SoundCategory.AMBIENT, 1.0f, 1.0f, true);
				Techguns.proxy.createFX("Impact_BulletMetal", world, x, y, z, 0.0D, 0.0D, 0.0D, m.pitch, m.yaw);
				
			} else if(m.soundType==4) { //dirt
				world.playSound(x, y, z, TGSounds.BULLET_IMPACT_DIRT, SoundCategory.AMBIENT, 1.0f, 1.0f, true);
		    	Techguns.proxy.createFX("Impact_BulletDirt", world, x, y, z, 0.0D, 0.0D, 0.0D, m.pitch, m.yaw);
		    	
			} else if(m.soundType==5) { //dirt, only sound
	    		world.playSound(x, y, z, TGSounds.BULLET_IMPACT_DIRT, SoundCategory.AMBIENT, 1.0f, 1.0f, true);

			} else if(m.soundType==6) { //dirt, only sound
	    		world.playSound(x, y, z, TGSounds.BULLET_IMPACT_BRICKS, SoundCategory.AMBIENT, 1.0f, 1.0f, true);
				Techguns.proxy.createFX("Impact_BulletRock", world, x, y, z, 0.0D, 0.0D, 0.0D, m.pitch, m.yaw);

			} else if (m.soundType==-1){ // default
				world.playSound(x, y, z, TGSounds.BULLET_IMPACT_DIRT, SoundCategory.AMBIENT, 1.0f, 1.0f, true);
		    	Techguns.proxy.createFX("Impact_BulletDefault", world, x, y, z, 0.0D, 0.0D, 0.0D, m.pitch, m.yaw);
				//this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D);
			}		
			
		}
		
	}
}
