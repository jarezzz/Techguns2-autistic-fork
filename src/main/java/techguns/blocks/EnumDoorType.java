package techguns.blocks;

import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;

public enum EnumDoorType implements IStringSerializable {
	METAL,
	HANGAR_UP,
	HANGAR_DOWN,
	NETHER;

	@Override
	public @NotNull String getName() {
		return name().toLowerCase();
	}

}
