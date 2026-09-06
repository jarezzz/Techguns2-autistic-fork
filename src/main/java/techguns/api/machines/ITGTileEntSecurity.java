package techguns.api.machines;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

public interface ITGTileEntSecurity {
	void setOwner(EntityPlayer ply);
	boolean isOwnedByPlayer(EntityPlayer ply);
	UUID getOwner();
	
	/**
	 * 0 - everyone
	 * 1 - friends if FTButilities installed, otherwise like 0
	 * 2 - owner only
	 * @return
	 */
	byte getSecurity();
}

