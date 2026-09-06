package techguns.api.npc;

/**
 *	Must only be implemented by subclasses of EntityLivingBase
 */
public interface INPCTechgunsShooter {
	float getWeaponPosX();
	float getWeaponPosY();
	float getWeaponPosZ();
	default boolean hasWeaponArmPose() {
		return true;
	}
	default float getGunScale() {
		return 1.0f;
	}
	
	default float getBulletOffsetSide() {
		return 0f;
	}
	default float getBulletOffsetHeight() {
		return 0f;
	}
}
