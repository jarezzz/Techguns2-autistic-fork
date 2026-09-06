package techguns.api.guns;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IGenericGun {

	boolean isShootWithLeftClick();

	boolean isSemiAuto();

	@SideOnly(Side.CLIENT)
	boolean isZooming();

	void shootGunPrimary(ItemStack stack, World world, EntityPlayer player, boolean zooming, EnumHand hand, Entity target);

	int getAmmoLeft(ItemStack stack);
	
	GunHandType getGunHandType();

	boolean isHoldZoom();

	float getZoomMult();

	default boolean canCharge() {return false;}

	ResourceLocation getCurrentTexture(ItemStack stack);
}
