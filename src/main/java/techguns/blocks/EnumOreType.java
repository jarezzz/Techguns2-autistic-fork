package techguns.blocks;

import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;

public enum EnumOreType implements IStringSerializable{
	ORE_COPPER(4.0f,1),
	ORE_TIN(4.0f,1),
	ORE_LEAD(6.0f,2),
	ORE_TITANIUM(8.0f,3),
	ORE_URANIUM(7.0f,2,4);

	private final float hardness;
	private final int mininglevel;
	private final int lightlevel;

	EnumOreType(float hardness, int mininglevel) {
		this(hardness,mininglevel,0);
	}
	
	EnumOreType(float hardness, int mininglevel, int lightlevel) {
		this.hardness = hardness;
		this.mininglevel = mininglevel;
		this.lightlevel = lightlevel;
	}

	public boolean isEnabled() {
		return true;
	}

	public float getHardness() {
		return hardness;
	}

	public int getMininglevel() {
		return mininglevel;
	}

	public int getLightlevel() {
		return lightlevel;
	}

	@Override
	public @NotNull String getName() {
		return this.name().toLowerCase();
	}

}
