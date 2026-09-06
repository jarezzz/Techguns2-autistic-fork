package techguns.blocks;

import net.minecraft.util.IStringSerializable;

public interface IEnumOreClusterType extends IStringSerializable {

	int getMiningLevel();
	float getMultiplier();
	
}
