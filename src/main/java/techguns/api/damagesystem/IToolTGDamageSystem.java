package techguns.api.damagesystem;

import net.minecraft.util.DamageSource;
import techguns.damagesystem.TGDamageSource;

public interface IToolTGDamageSystem {
	TGDamageSource getDamageSource(DamageSource original);
}
