package techguns.api.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

/**
 * Should only be used on EntityPlayer
 * 
 */
public interface ITGExtendedPlayer extends ITGShooterValues {

	EntityPlayer getEntity();
	
	int getFireDelay(EnumHand hand);
	void setFireDelay(EnumHand hand, int delay);
	IInventory getTGInventory();
	
	void saveToNBT(final NBTTagCompound tags);
	void loadFromNBT(final NBTTagCompound tags);
    boolean hasFabricatorRecipeUnlocked(ItemStack output);
    boolean unlockFabricatorRecipe(ItemStack output);
}
