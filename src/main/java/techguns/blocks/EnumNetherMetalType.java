package techguns.blocks;

import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;

public enum EnumNetherMetalType implements IStringSerializable, IEnumLightlevel {
	PANEL,
	GRATE1,
	GRATE2,
	GREY_DARK,
	GREY,
	GREY_TILES,
	BORDER_RED,
	PLATE_BLACK,
	PLATE_RED,
	BORDER_LAVA(15);

	private int lightlevel = 0;
	
	EnumNetherMetalType() {}

	EnumNetherMetalType(int lightlevel) {
		this.lightlevel = lightlevel;
	}
	
	@Override
	public @NotNull String getName() {
		return name().toLowerCase();
	}

	@Override
	public int getLightlevel() {
		return lightlevel;
	}
}
