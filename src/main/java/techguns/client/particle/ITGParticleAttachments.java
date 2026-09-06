package techguns.client.particle;

import net.minecraft.util.EnumHand;

import java.util.Iterator;
import java.util.List;

/**
 * Implemented by capabilities that store entityAttachedParticle systems
 *
 */
public interface ITGParticleAttachments {
	
	default void tickParticles() {
		if(this.getEntityParticles()!=null) {
			Iterator<ITGParticle> it = getEntityParticles().iterator();
			while(it.hasNext()) {
				ITGParticle p = it.next();
				
				p.updateTick();
				if(p.shouldRemove()) {
					it.remove();
				}
			}
		}
		
		if(this.getEntityParticlesMH()!=null) {
			Iterator<ITGParticle> it = getEntityParticlesMH().iterator();
			while(it.hasNext()) {
				ITGParticle p = it.next();
				
				p.updateTick();
				if(p.shouldRemove()) {
					it.remove();
				}
			}
		}
		
		if(this.getEntityParticlesOH()!=null) {
			Iterator<ITGParticle> it = getEntityParticlesOH().iterator();
			while(it.hasNext()) {
				ITGParticle p = it.next();
				
				p.updateTick();
				if(p.shouldRemove()) {
					it.remove();
				}
			}
		}
		
		if(this.getParticleSysMainhand()!=null) {
			Iterator<TGParticleSystem> it = getParticleSysMainhand().iterator();
			while(it.hasNext()) {
				TGParticleSystem p = it.next();
				
				p.updateTick();
				if(p.shouldRemove()) {
					it.remove();
				}
			}
		}
		if(this.getParticleSysOffhand()!=null) {
			Iterator<TGParticleSystem> it = getParticleSysOffhand().iterator();
			while(it.hasNext()) {
				TGParticleSystem p = it.next();
				
				p.updateTick();
				if(p.shouldRemove()) {
					it.remove();
				}
			}
		}
	}
	
	default void clearAttachedSystemsHand(EnumHand hand) {
		if(hand==EnumHand.MAIN_HAND) {
			if (this.getParticleSysMainhand()!=null) {
				this.getParticleSysMainhand().clear();
			}
		} else {
			if (this.getParticleSysOffhand()!=null) {
				this.getParticleSysOffhand().clear();
			}
		}
	}
	
	default void addSystemsHand(EnumHand hand, List<TGParticleSystem> systems) {
		
		if(hand==EnumHand.MAIN_HAND) {
			this.getOrInitParticleSysMainhand().clear();
			this.getParticleSysMainhand().addAll(systems);
		} else {
			this.getOrInitParticleSysOffhand().clear();
			this.getParticleSysOffhand().addAll(systems);
		}
	}
	
	default void addEffectHand(EnumHand hand, List<ITGParticle> effects) {
		
		if(hand==EnumHand.MAIN_HAND) {
			this.getOrInitEntityParticlesMH().addAll(effects);
		} else {
			this.getOrInitEntityParticlesOH().addAll(effects);
		}
	}
	
	List<ITGParticle> getEntityParticles();
	
	List<ITGParticle> getEntityParticlesMH();
	
	List<ITGParticle> getEntityParticlesOH();
	
	List<TGParticleSystem> getParticleSysMainhand();
	
	List<TGParticleSystem> getParticleSysOffhand();

	List<ITGParticle> getOrInitEntityParticlesOH();

	List<ITGParticle> getOrInitEntityParticlesMH();
	
	List<TGParticleSystem> getOrInitParticleSysMainhand();
	
	List<TGParticleSystem> getOrInitParticleSysOffhand();

}
