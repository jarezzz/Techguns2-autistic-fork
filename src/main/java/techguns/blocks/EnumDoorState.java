package techguns.blocks;

import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;

public enum EnumDoorState implements IStringSerializable {
	CLOSED,
	OPENED,
	OPENING,
	CLOSING;

	@Override
	public @NotNull String getName() {
		return name().toLowerCase();
	}

}
