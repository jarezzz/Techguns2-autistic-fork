package techguns.blocks;

import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;

public enum EnumLadderType implements IStringSerializable {
	METAL,
	SHINY,
	RUSTY,
	CARBON;

	@Override
	public @NotNull String getName() {
		return this.name().toLowerCase();
	}
}