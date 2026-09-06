package techguns.world.dungeon.presets.specialblocks;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.util.MBlock;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class MBlockSkull extends MBlock {

	protected String playerUUID;
	protected String playerName;
	protected String textureValue;
	protected int skullType;
	protected int skullRotation;

	public MBlockSkull(Block block, int meta, int skullType, int skullRotation,
					   @Nullable String playerUUID, @Nullable String playerName, @Nullable String textureValue) {
		super(block, meta);
		this.skullType = skullType;
		this.skullRotation = skullRotation;
		this.playerUUID = playerUUID;
		this.playerName = playerName;
		this.textureValue = textureValue;
		this.hasTileEntity = true;
	}

	public MBlockSkull(int skullType, int skullRotation,
					   @Nullable String playerUUID, @Nullable String playerName, @Nullable String textureValue) {
		this(Blocks.SKULL, 1, skullType, skullRotation, playerUUID, playerName, textureValue);
	}

	public MBlockSkull(MBlock other) {
		super(other);
		this.skullType = 0;
		this.skullRotation = 0;
		this.playerUUID = null;
		this.playerName = null;
		this.textureValue = null;
		this.hasTileEntity = true;
	}

	@Override
	public void tileEntityPostPlacementAction(World w, IBlockState state, BlockPos p, int rotation) {
		TileEntity tile = w.getTileEntity(p);
		if (tile instanceof TileEntitySkull skullTile) {

			skullTile.setType(this.skullType);

			EnumFacing facing = state.getValue(BlockSkull.FACING);
			if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
				int newRotation = (this.skullRotation + rotation * 4) % 16;
				skullTile.setSkullRotation(newRotation);
			}

			if (this.skullType == 3 && (this.playerUUID != null || this.playerName != null || this.textureValue != null)) {
				UUID uuid = null;
				if (this.playerUUID != null) {
					try {
						uuid = UUID.fromString(this.playerUUID);
					} catch (IllegalArgumentException ignored) {}
				}

				GameProfile profile = new GameProfile(uuid, this.playerName);

				if (this.textureValue != null) {
					profile.getProperties().put("textures", new Property("textures", this.textureValue));
				}

				skullTile.setPlayerProfile(profile);
			}

			skullTile.markDirty();
		}
	}

	public String getPlayerUUID() {
		return playerUUID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getTextureValue() {
		return textureValue;
	}

	public int getSkullType() {
		return skullType;
	}

	public int getSkullRotation() {
		return skullRotation;
	}

	@Override
	public boolean equals(Object other) {
		if (!super.equals(other)) return false;
		if (!(other instanceof MBlockSkull otherSkull)) return false;
		return this.skullType == otherSkull.skullType
				&& this.skullRotation == otherSkull.skullRotation
				&& Objects.equals(this.playerUUID, otherSkull.playerUUID)
				&& Objects.equals(this.playerName, otherSkull.playerName)
				&& Objects.equals(this.textureValue, otherSkull.textureValue);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + skullType;
		result = 31 * result + skullRotation;
		result = 31 * result + (playerUUID != null ? playerUUID.hashCode() : 0);
		result = 31 * result + (playerName != null ? playerName.hashCode() : 0);
		result = 31 * result + (textureValue != null ? textureValue.hashCode() : 0);
		return result;
	}
}