package techguns.blocks;

import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;

public enum EnumTGSlimyType implements IStringSerializable {
	BUGNEST_EGGS;

	@Override
	public @NotNull String getName() {
		return name().toLowerCase();
	}
}
