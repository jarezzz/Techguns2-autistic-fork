package techguns.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import org.jetbrains.annotations.NotNull;
import techguns.*;
import techguns.api.capabilities.ITGShooterValues;

public class TGShooterValuesCapProvider implements ICapabilitySerializable<NBTBase> {

	@CapabilityInject(ITGShooterValues.class)
	public static final Capability<ITGShooterValues> TG_SHOOTER_VALUES = null;
	
	/**
	 * The ID of this capability.
	 */
	public static final ResourceLocation ID = new ResourceLocation(Tags.MOD_ID, "shooterValues");
	
	public static final EnumFacing DEFAULT_FACING = null;
	
	private ITGShooterValues instance; // = TG_SHOOTER_VALUES.getDefaultInstance();
		
	
	public TGShooterValuesCapProvider(ITGShooterValues caps) {
		this.instance = caps;
	}

	@Override
	public boolean hasCapability(@NotNull Capability<?> capability, EnumFacing facing) {
		return false;
	}

	@Override
	public <T> T getCapability(@NotNull Capability<T> capability, EnumFacing facing) {
		return capability == TG_SHOOTER_VALUES ? TG_SHOOTER_VALUES.cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		 return TG_SHOOTER_VALUES.getStorage().writeNBT(TG_SHOOTER_VALUES, this.instance, DEFAULT_FACING);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		TG_SHOOTER_VALUES.getStorage().readNBT(TG_SHOOTER_VALUES, this.instance, DEFAULT_FACING, nbt);
	}
}
