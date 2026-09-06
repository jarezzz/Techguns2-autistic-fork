package techguns.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.storage.loot.LootTableList;
import org.jetbrains.annotations.NotNull;
import techguns.*;

public class BlockMilitaryCrate extends GenericBlockMetaEnum<EnumMilitaryCrateType>{

	protected static final ResourceLocation loottable_ammo = new ResourceLocation(Tags.MOD_ID, "blocks/military_crate_ammo");
	protected static final ResourceLocation loottable_gun = new ResourceLocation(Tags.MOD_ID, "blocks/military_crate_gun");
	protected static final ResourceLocation loottable_armor = new ResourceLocation(Tags.MOD_ID, "blocks/military_crate_armor");
	protected static final ResourceLocation loottable_medical = new ResourceLocation(Tags.MOD_ID, "blocks/military_crate_medical");
	protected static final ResourceLocation loottable_explosives = new ResourceLocation(Tags.MOD_ID, "blocks/military_crate_explosives");
	protected static final ResourceLocation loottable_generic = new ResourceLocation(Tags.MOD_ID, "blocks/military_crate_generic");
	
	protected static final AxisAlignedBB boundingbox = new AxisAlignedBB(0.03125, 0, 0.03125, 0.96875, 1, 0.96875);
	
	static {
		LootTableList.register(loottable_ammo);
		LootTableList.register(loottable_gun);
		LootTableList.register(loottable_armor);
		LootTableList.register(loottable_medical);
		LootTableList.register(loottable_explosives);
		LootTableList.register(loottable_generic);
	}
	
	public BlockMilitaryCrate(String name, Material mat) {
		super(name, mat, EnumMilitaryCrateType.class);
	}

	@Override
	public @NotNull AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
		return boundingbox;
	}

	@Override
	public boolean isFullBlock(@NotNull IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(@NotNull IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos) {
		return false;
	}

	public ResourceLocation getLootableForState(IBlockState state) {
		switch(state.getValue(this.TYPE)) {
		case AMMO:
			return loottable_ammo;
		case ARMOR:
			return loottable_armor;
		case EXPLOSIVE:
			return loottable_explosives;
		case GUN:
			return loottable_gun;
		case MEDICAL:
			return loottable_medical;
		default:
			return loottable_generic;
		}
	}
	
	@Override
	public @NotNull BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull EnumFacing face) {
		if (face==EnumFacing.DOWN || face==EnumFacing.UP) {
			return BlockFaceShape.CENTER_BIG;
		}
		return BlockFaceShape.UNDEFINED;
	}
}
