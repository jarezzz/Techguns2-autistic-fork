package techguns.blocks;

import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;

public enum EnumLightblockType implements IStringSerializable {
	NEONTUBES2,
	NEONTUBES2_ROTATED,
	NEONTUBES4,
	NEONTUBES4_ROTATED,
	NEONSQUARE_WHITE,;

	@Override
	public @NotNull String getName() {
		return name().toLowerCase();
	}
}
