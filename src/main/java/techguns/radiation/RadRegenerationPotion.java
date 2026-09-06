package techguns.radiation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import techguns.capabilities.TGExtendedPlayer;
import techguns.gui.player.TGPlayerInventoryGui;

public class RadRegenerationPotion extends Potion {

	public RadRegenerationPotion() {
		super(false, 0xffa2000);
	}

	@Override
	public void renderInventoryEffect(int x, int y, @NotNull PotionEffect effect, @NotNull Minecraft mc) {
		super.renderInventoryEffect(x, y, effect, mc);

		if(mc.currentScreen!=null) {
			mc.getTextureManager().bindTexture(TGPlayerInventoryGui.texture);
			mc.currentScreen.drawTexturedModalRect(x+8, y+8, 32, 168, 16, 16);
			mc.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
		}
	}

	@Override
	public void renderHUDEffect(int x, int y, @NotNull PotionEffect effect, @NotNull Minecraft mc, float alpha) {
		super.renderHUDEffect(x, y, effect, mc, alpha);
		
		mc.getTextureManager().bindTexture(TGPlayerInventoryGui.texture);
		
		mc.ingameGUI.drawTexturedModalRect(x+4, y+4, 32, 168, 16, 16);
		mc.getTextureManager().bindTexture(GuiContainer.INVENTORY_BACKGROUND);
	}
	
	@Override
	public void performEffect(@NotNull EntityLivingBase elb, int amplifier) {
		
		int amount = (amplifier+1);
		
		if(elb instanceof EntityPlayer) {
			TGExtendedPlayer props = TGExtendedPlayer.get((EntityPlayer) elb);
			props.addRadiation(-amount);
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration % 20 == 0;
	}
}
