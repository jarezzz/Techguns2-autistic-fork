package techguns.items.guns;

import techguns.entities.projectiles.GenericProjectile;

public interface IProjectileBounceFactory<T extends GenericProjectile> extends IProjectileFactory<T> {

	T createBounceProjectile(T proj, double bounceX, double bounceY, double bounceZ);

}
