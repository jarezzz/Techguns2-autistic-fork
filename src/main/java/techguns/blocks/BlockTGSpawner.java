package techguns.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.jetbrains.annotations.NotNull;
import techguns.*;
import techguns.entities.npcs.ArmySoldier;
import techguns.entities.npcs.ZombieSoldier;
import techguns.tileentities.TGSpawnerTileEnt;

public class BlockTGSpawner extends GenericBlockMetaEnum<EnumMonsterSpawnerType> {
	
	public BlockTGSpawner(String name) {
		super(name, Material.ROCK, EnumMonsterSpawnerType.class);
		this.setBlockUnbreakable();
	}

	protected static final AxisAlignedBB BB = new AxisAlignedBB(2/16d, 0, 2/16d, 14/16d, 2/16d, 14/16d);
	
	
	
	@Override
	public void registerBlock(Register<Block> event) {
		super.registerBlock(event);
		GameRegistry.registerTileEntity(TGSpawnerTileEnt.class, Tags.MOD_ID+":"+"TGSpawner");
	}

	@Override
	public boolean hasTileEntity(@NotNull IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(@NotNull World world, @NotNull IBlockState state) {
		return new TGSpawnerTileEnt();
	}

	@Override
	public boolean isFullBlock(@NotNull IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(@NotNull IBlockState state) {
		return false;
	}

	@Override
	public @NotNull AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
		return BB;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(@NotNull IBlockState blockState, @NotNull IBlockAccess worldIn, @NotNull BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public boolean isOpaqueCube(@NotNull IBlockState state) {
		return false;
	}

	@Override
	public void getDrops(@NotNull NonNullList<ItemStack> drops, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull IBlockState state,
						 int fortune) {
	}

	@Override
	public void onBlockPlacedBy(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityLivingBase placer,
								@NotNull ItemStack stack) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof TGSpawnerTileEnt) {
			TGSpawnerTileEnt spawner = (TGSpawnerTileEnt) tile;
			
			switch(state.getValue(TYPE)) {
			case HOLE:
				spawner.addMobType(ZombieSoldier.class, 1);
				break;
			case SOLDIER_SPAWN:
				spawner.addMobType(ArmySoldier.class, 1);
				break;
			default:
				break;
			
			}
		}
	}

	@Override
	public @NotNull BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

}
