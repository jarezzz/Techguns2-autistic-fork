package techguns.blocks;

import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;

public enum EnumLampType implements IStringSerializable {
	YELLOW,
	WHITE,
	YELLOW_LANTERN,
	WHITE_LANTERN;

	@Override
	public @NotNull String getName() {
		return this.name().toLowerCase();
	}
}
