package techguns.blocks;

import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;

public enum EnumMilitaryCrateType implements IStringSerializable {
	AMMO,
	GUN,
	ARMOR,
	MEDICAL,
	EXPLOSIVE,
	GENERIC_OAK,
	GENERIC_JUNGLE,
	GENERIC_BIRCH,
	GENERIC_SPRUCE;

	@Override
	public @NotNull String getName() {
		return name().toLowerCase();
	}

}
