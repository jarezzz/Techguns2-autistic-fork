package techguns.api.capabilities;

public interface ITGShooterValues {

	AttackTime getAttackTime(boolean offHand);
	boolean isRecoiling(boolean offHand);
	boolean isReloading(boolean offHand);
}
