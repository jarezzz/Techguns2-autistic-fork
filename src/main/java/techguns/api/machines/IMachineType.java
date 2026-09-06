package techguns.api.machines;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IMachineType {
	int getIndex();
	int getMaxMachineIndex();
	TileEntity getTile();
	Class<? extends TileEntity> getTileClass();
	EnumBlockRenderType getRenderType();
	boolean isFullCube();
	BlockRenderLayer getBlockRenderLayer();
	boolean debugOnly();
	default boolean hideInCreative() {
		return false;
	}
	default SoundType getSoundType() {
		return SoundType.METAL;
	}
	
	default boolean hasCustomModelLocation() {
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	default void setCustomModelLocation(Item itemblock, int meta, ResourceLocation registryName, IBlockState state) {
		//do nothing
	}
	
	default AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return Block.FULL_BLOCK_AABB;
	}
}
