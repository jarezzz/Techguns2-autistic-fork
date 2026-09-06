package techguns.blocks.machines;

import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;
import org.jetbrains.annotations.NotNull;
import techguns.api.machines.IMachineType;
import techguns.tileentities.OreDrillTileEntSlave;
import techguns.tileentities.OreDrillTileEntMaster;

public enum EnumOreDrillType implements IStringSerializable, IMachineType {
	FRAME(0,OreDrillTileEntSlave.class,true,EnumBlockRenderType.MODEL),
	SCAFFOLD(1,OreDrillTileEntSlave.class,false,EnumBlockRenderType.MODEL,BlockRenderLayer.CUTOUT,SoundType.METAL, false),
	ROD(2,OreDrillTileEntSlave.class,true,EnumBlockRenderType.MODEL),
	ENGINE(3,OreDrillTileEntSlave.class,true,EnumBlockRenderType.MODEL),
	CONTROLLER(4,OreDrillTileEntMaster.class,true,EnumBlockRenderType.MODEL),
	SCAFFOLD_HIDDEN(5,OreDrillTileEntSlave.class,false,EnumBlockRenderType.INVISIBLE,BlockRenderLayer.CUTOUT,SoundType.METAL, true)
	;

	private final int id;
	private final String name;
	private final Class<? extends TileEntity> tile;
	private final boolean isFullCube;
	private final EnumBlockRenderType renderType;
	private final BlockRenderLayer renderLayer;
	private final SoundType soundType;
	private final boolean hideInCreative;
	
	EnumOreDrillType(int id, Class<? extends TileEntity> tile, boolean isFullCube, EnumBlockRenderType renderType) {
		this(id,tile,isFullCube,renderType,BlockRenderLayer.SOLID,SoundType.METAL, false);
	}
	
	EnumOreDrillType(int id, Class<? extends TileEntity> tile, boolean isFullCube, EnumBlockRenderType renderType, BlockRenderLayer layer, SoundType sound, boolean hideInCreative) {
		this.id=id;
		this.name=this.name().toLowerCase();
		this.tile = tile;
		this.isFullCube=isFullCube;
		this.renderType=renderType;
		this.renderLayer=layer;
		this.soundType=sound;
		this.hideInCreative=hideInCreative;
	}
	
	@Override
	public int getIndex() {
		return id;
	}
	
	@Override
	public @NotNull String getName() {
		return this.name;
	}

	@Override
	public int getMaxMachineIndex() {
		return EnumOreDrillType.values().length;
	}

	@Override
	public TileEntity getTile() {
		try {
			return this.tile.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SoundType getSoundType() {
		return this.soundType;
	}

	@Override
	public Class<? extends TileEntity> getTileClass() {
		return this.tile;
	}

	@Override
	public EnumBlockRenderType getRenderType() {
		return this.renderType;
	}

	@Override
	public boolean isFullCube() {
		return this.isFullCube;
	}

	@Override
	public BlockRenderLayer getBlockRenderLayer() {
		return this.renderLayer;
	}

	@Override
	public boolean debugOnly() {
		return false;
	}

	@Override
	public boolean hideInCreative() {
		return hideInCreative;
	}

	
}
