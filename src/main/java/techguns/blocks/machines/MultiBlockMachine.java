package techguns.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import techguns.api.machines.IMachineType;
import techguns.tileentities.MultiBlockMachineTileEntMaster;
import techguns.tileentities.MultiBlockMachineTileEntSlave;
import techguns.util.BlockUtils;

import java.util.Objects;

public class MultiBlockMachine<T extends Enum<T> & IStringSerializable & IMachineType> extends BasicMachine<T> {
	public static final PropertyBool FORMED = PropertyBool.create("formed");
	
	public static final PropertyDirection MULTIBLOCK_DIRECTION = BlockHorizontal.FACING; 
	
	public MultiBlockMachine(String name, Class<T> clazz) {
		super(name, clazz);
		this.blockStateOverride = new BlockStateContainer.Builder(this).add(MACHINE_TYPE).add(FORMED).add(MULTIBLOCK_DIRECTION).build();
		this.setDefaultState(this.getBlockState().getBaseState().withProperty(FORMED, false).withProperty(MULTIBLOCK_DIRECTION, EnumFacing.SOUTH));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (state.getValue(FORMED) ? 8 : 0) + state.getValue(MACHINE_TYPE).getIndex();
	}
	
	@Override
	public @NotNull IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FORMED, meta >= 8).withProperty(MACHINE_TYPE, clazz.getEnumConstants()[meta >= 8 ? meta - 8 : meta]);
	}
	
	@Override
	public @NotNull IBlockState getActualState(@NotNull IBlockState state, IBlockAccess world, @NotNull BlockPos pos) {
		IBlockState s = world.getBlockState(pos);
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof MultiBlockMachineTileEntMaster) {
			MultiBlockMachineTileEntMaster mastertile = (MultiBlockMachineTileEntMaster) tile;
			if(mastertile.isFormed()) {
				EnumFacing dir = mastertile.getMultiblockDirection();
				s = s.withProperty(MULTIBLOCK_DIRECTION, dir);
				return s;
			}
		}
		return s.withProperty(MULTIBLOCK_DIRECTION, EnumFacing.SOUTH);
	}

	/**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
	@Override
    public @NotNull IBlockState getStateForPlacement(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, @NotNull EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(FORMED,false);
    }

	@Override
	public @NotNull EnumBlockRenderType getRenderType(IBlockState state) {
		T t = state.getValue(MACHINE_TYPE);
		return t.getRenderType();
	}
	
	@Override
	public @NotNull ItemStack getPickBlock(IBlockState state, @NotNull RayTraceResult target, @NotNull World world, @NotNull BlockPos pos,
										   @NotNull EntityPlayer player) {
		if(state.getValue(MACHINE_TYPE).hideInCreative()) {
			return ItemStack.EMPTY;
		}
		return super.getPickBlock(state, target, world, pos, player);
	}

	@Override
	public boolean isFullBlock(@NotNull IBlockState state) {
		return this.isFullCube(state);
	}

	@Override
	public boolean isFullCube(@NotNull IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(@NotNull IBlockState state) {
		
		/**
		 * Required to check this, because vanilla block constructor calls this method too early.
		 */
		Block b= state.getBlock();
		if(b.getRegistryName()==null) {
			return false;
		}
		return this.isFullCube(state);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, @NotNull IBlockAccess worldIn, @NotNull BlockPos pos) {
		if(blockState.getValue(FORMED)) {
			
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof MultiBlockMachineTileEntSlave) {
				return ((MultiBlockMachineTileEntSlave) tile).getFormedCollisionBoundingBox();
			}
			
		}
		
		return FULL_BLOCK_AABB;
	}
	
	@Override
	public @NotNull AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
		return Objects.requireNonNull(this.getCollisionBoundingBox(state, source, pos));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemBlockModels() {
		for(int i = 0; i< clazz.getEnumConstants().length;i++) {
			IBlockState state = getDefaultState().withProperty(MACHINE_TYPE, clazz.getEnumConstants()[i]);
			ModelLoader.setCustomModelResourceLocation(this.itemblock, this.getMetaFromState(state), new ModelResourceLocation(getRegistryName(),BlockUtils.getBlockStateVariantString(state)));
		}
	}

	@Override
	public boolean rotateBlock(@NotNull World world, @NotNull BlockPos pos, @NotNull EnumFacing axis) {
		//NO ROTATING ON MULTIBLOCK MACHINES
		return false;
	}

	@Override
	public @NotNull BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, IBlockState state, @NotNull BlockPos pos, @NotNull EnumFacing face) {
		boolean formed = state.getValue(FORMED);
		if(formed) {
			return BlockFaceShape.UNDEFINED;
		}
		return super.getBlockFaceShape(worldIn, state, pos, face);
	}
	
}
