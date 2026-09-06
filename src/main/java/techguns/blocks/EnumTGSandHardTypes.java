package techguns.blocks;

import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;

public enum EnumTGSandHardTypes implements IStringSerializable{
	BUGNEST_SAND;

	@Override
	public @NotNull String getName() {
		return name().toLowerCase();
	}
}
