package techguns.client.particle;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public abstract class TGFXType {
	public String name;
	boolean isList = false;
	
	public abstract List<TGParticleSystem> createParticleSystems(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ);
	public abstract List<TGParticleSystem> createParticleSystemsOnEntity(Entity ent);
	public abstract List<TGParticleSystem> createParticleSystemsOnParticle(World worldIn, TGParticle part);
	public abstract List<TGParticleSystem> createParticleSystemsOnEntityItemAttached(Entity ent, EnumHand hand);
}
