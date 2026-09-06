package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BlockSandbags extends GenericBlock {

    /** Whether this block connects in the northern direction */
    public static final PropertyBool NORTH = PropertyBool.create("north");
    /** Whether this block connects in the eastern direction */
    public static final PropertyBool EAST = PropertyBool.create("east");
    /** Whether this block connects in the southern direction */
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    /** Whether this block connects in the western direction */
    public static final PropertyBool WEST = PropertyBool.create("west");
    
    public static final PropertyBool CORNER_NE = PropertyBool.create("corner_ne");
    public static final PropertyBool CORNER_ES = PropertyBool.create("corner_es");
    public static final PropertyBool CORNER_SW = PropertyBool.create("corner_sw");
    public static final PropertyBool CORNER_WN = PropertyBool.create("corner_wn");
    
    
    public static final double size_1=0.25D;
    public static final double size_2=0.75D;
    public static final double h = 1.0D;
    
    protected static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[] {new AxisAlignedBB(size_1, 0.0D, size_1, size_2, 1.0D, size_2), new AxisAlignedBB(size_1, 0.0D, size_1, size_2, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, size_1, size_2, 1.0D, size_2), new AxisAlignedBB(0.0D, 0.0D, size_1, size_2, 1.0D, 1.0D), new AxisAlignedBB(size_1, 0.0D, 0.0D, size_2, 1.0D, size_2), new AxisAlignedBB(size_1, 0.0D, 0.0D, size_2, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, size_2, 1.0D, size_2), new AxisAlignedBB(0.0D, 0.0D, 0.0D, size_2, 1.0D, 1.0D), new AxisAlignedBB(size_1, 0.0D, size_1, 1.0D, 1.0D, size_2), new AxisAlignedBB(size_1, 0.0D, size_1, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, size_1, 1.0D, 1.0D, size_2), new AxisAlignedBB(0.0D, 0.0D, size_1, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(size_1, 0.0D, 0.0D, 1.0D, 1.0D, size_2), new AxisAlignedBB(size_1, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, size_2), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
    public static final AxisAlignedBB PILLAR_AABB = new AxisAlignedBB(size_1, 0.0D, size_1, size_2, h, size_2);
    public static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(size_1, 0.0D, size_2, size_2, h, 1.0D);
    public static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, size_1, size_1, h, size_2);
    public static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(size_1, 0.0D, 0.0D, size_2, h, size_1);
    public static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(size_2, 0.0D, size_1, 1.0D, h, size_2);
	
    protected GenericItemBlock itemblock;
    
	public BlockSandbags(String name) {
		super(name, Material.CLOTH, MapColor.BROWN);
		this.setSoundType(SoundType.CLOTH);
		this.setHardness(3.0f);
		this.setResistance(15.0f);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false));  
	}

	@Override
	 public void addCollisionBoxToList(@NotNull IBlockState state, @NotNull World worldIn, @NotNull BlockPos pos, @NotNull AxisAlignedBB entityBox, @NotNull List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
	    {
		if (!p_185477_7_)
	        {
	            state = state.getActualState(worldIn, pos);
	        }

	        addCollisionBoxToList(pos, entityBox, collidingBoxes, PILLAR_AABB);

	        if (state.getValue(NORTH))
	        {
	            addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
	        }

	        if (state.getValue(EAST))
	        {
	            addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
	        }

	        if (state.getValue(SOUTH))
	        {
	            addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
	        }

	        if (state.getValue(WEST))
	        {
	            addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
	        }
	    }

		@Override
	    public @NotNull AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos)
	    {
	        state = this.getActualState(state, source, pos);
	        return BOUNDING_BOXES[getBoundingBoxIdx(state)];
	    }

	    /**
	     * Returns the correct index into boundingBoxes, based on what the fence is connected to.
	     */
	    private static int getBoundingBoxIdx(IBlockState state)
	    {
	        int i = 0;

	        if (state.getValue(NORTH))
	        {
	            i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
	        }

	        if (state.getValue(EAST))
	        {
	            i |= 1 << EnumFacing.EAST.getHorizontalIndex();
	        }

	        if (state.getValue(SOUTH))
	        {
	            i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
	        }

	        if (state.getValue(WEST))
	        {
	            i |= 1 << EnumFacing.WEST.getHorizontalIndex();
	        }

	        return i;
	    }
	
	    /**
	     * Used to determine ambient occlusion and culling when rebuilding chunks for render
	     */
	    public boolean isOpaqueCube(@NotNull IBlockState state)
	    {
	        return false;
	    }

	    public boolean isFullCube(@NotNull IBlockState state)
	    {
	        return false;
	    }

	    /**
	     * Determines if an entity can path through this block
	     */
	    public boolean isPassable(@NotNull IBlockAccess worldIn, @NotNull BlockPos pos)
	    {
	        return false;
	    }

	    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing facing)
	    {
	        IBlockState iblockstate = worldIn.getBlockState(pos);
	        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos, facing);
	        Block block = iblockstate.getBlock();
	        boolean flag = blockfaceshape == BlockFaceShape.MIDDLE_POLE && (iblockstate.getMaterial() == this.material || block instanceof BlockFenceGate);
	        return !isExcepBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID || flag;
	    }

	    protected static boolean isExcepBlockForAttachWithPiston(Block p_194142_0_)
	    {
	        return Block.isExceptBlockForAttachWithPiston(p_194142_0_) || p_194142_0_ == Blocks.BARRIER || p_194142_0_ == Blocks.MELON_BLOCK || p_194142_0_ == Blocks.PUMPKIN || p_194142_0_ == Blocks.LIT_PUMPKIN;
	    }

	    @SideOnly(Side.CLIENT)
	    public boolean shouldSideBeRendered(@NotNull IBlockState blockState, @NotNull IBlockAccess blockAccess, @NotNull BlockPos pos, @NotNull EnumFacing side)
	    {
	        return true;
	    }

	    /**
	     * Convert the BlockState into the correct metadata value
	     */
	    @Override
	    public int getMetaFromState(@NotNull IBlockState state)
	    {
	        return 0;
	    }

	    /**
	     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	     * metadata, such as fence connections.
	     */
	    @Override
	    public @NotNull IBlockState getActualState(IBlockState state, @NotNull IBlockAccess worldIn, @NotNull BlockPos pos)
	    {
	        return state.withProperty(NORTH, canFenceConnectTo(worldIn, pos, EnumFacing.NORTH))
	                    .withProperty(EAST,  canFenceConnectTo(worldIn, pos, EnumFacing.EAST))
	                    .withProperty(SOUTH, canFenceConnectTo(worldIn, pos, EnumFacing.SOUTH))
	                    .withProperty(WEST,  canFenceConnectTo(worldIn, pos, EnumFacing.WEST))
	        			.withProperty(CORNER_NE,  canConnectCorner(worldIn, pos, EnumFacing.NORTH, EnumFacing.EAST))
	        			.withProperty(CORNER_ES,  canConnectCorner(worldIn, pos, EnumFacing.EAST, EnumFacing.SOUTH))
	        			.withProperty(CORNER_SW,  canConnectCorner(worldIn, pos, EnumFacing.SOUTH, EnumFacing.WEST))
	        			.withProperty(CORNER_WN,  canConnectCorner(worldIn, pos, EnumFacing.WEST, EnumFacing.NORTH));
	    }
	    
	    private boolean canConnectCorner(IBlockAccess world, BlockPos pos, EnumFacing f1, EnumFacing f2) {
	    	return canFenceConnectTo(world, pos, f1) && canFenceConnectTo(world, pos, f2) && (canFenceConnectTo(world, pos.offset(f1), f2) || canFenceConnectTo(world, pos.offset(f2), f1));
	    }

	    @Override
	    protected @NotNull BlockStateContainer createBlockState()
	    {
	        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH, CORNER_NE, CORNER_ES, CORNER_SW, CORNER_WN);
	    }
	    
	    @Override
	    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, @NotNull EnumFacing facing)
	    {
	        Block connector = world.getBlockState(pos.offset(facing)).getBlock();

	        if(connector instanceof BlockFence) return true;
	        else return connector instanceof BlockSandbags;
		}

	    private boolean canFenceConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing)
	    {
	        BlockPos other = pos.offset(facing);
	        Block block = world.getBlockState(other).getBlock();
	        return block.canBeConnectedTo(world, other, facing.getOpposite()) || canConnectTo(world, other, facing.getOpposite());
	    }

		@Override
		public void registerItemBlockModels() {
			ModelLoader.setCustomModelResourceLocation(this.itemblock, 0, new ModelResourceLocation(getRegistryName()+"_inventory","inventory"));
			
		}

		@Override
		public ItemBlock createItemBlock() {
			this.itemblock=new GenericItemBlock(this);
			return this.itemblock;
		}

		@Override
		public @NotNull BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess world, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull EnumFacing facing) {
			if(facing==EnumFacing.UP || facing==EnumFacing.DOWN) {
				return BlockFaceShape.MIDDLE_POLE_THICK;
			} else {
				return BlockFaceShape.UNDEFINED;
			}
		}

		@Override
		public boolean canPlaceTorchOnTop(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos) {
			return true;
		}
		
}
