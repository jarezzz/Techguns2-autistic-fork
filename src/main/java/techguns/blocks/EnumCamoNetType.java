package techguns.blocks;

import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;

public enum EnumCamoNetType implements IStringSerializable {
	WOOD,
	DESERT,
	SNOW;

	@Override
	public @NotNull String getName() {
		return name().toLowerCase();
	}
}
