package techguns.blocks;

import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;

public enum EnumMonsterSpawnerType implements IStringSerializable {
	HOLE,
	SOLDIER_SPAWN;

	@Override
	public @NotNull String getName() {
		return name().toLowerCase();
	}

}
