package techguns.api.radiation;

import net.minecraft.entity.ai.attributes.IAttribute;

public class TGRadiation {
	/**
	 * Radiation is calculated as Level of Radiation effect - radiation resistance.
	 * this field will be initialized in the pre-init phase of Techguns
	 */
	public static IAttribute RADIATION_RESISTANCE = null;
}
