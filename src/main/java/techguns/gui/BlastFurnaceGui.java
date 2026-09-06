package techguns.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import techguns.Tags;
import techguns.gui.containers.BlastFurnaceContainer;
import techguns.tileentities.BlastFurnaceTileEnt;

public class BlastFurnaceGui extends PoweredTileEntGui {

    public static final ResourceLocation texture = new ResourceLocation(Tags.MOD_ID, "textures/gui/blast_furnace_gui.png");

    protected BlastFurnaceTileEnt tile;

    public BlastFurnaceGui(InventoryPlayer ply, BlastFurnaceTileEnt tileent) {
        super(new BlastFurnaceContainer(ply, tileent), tileent);
        this.tile = tileent;
        this.tex = texture;
        this.appearanceType = EnumAppearanceType.REGULAR;
        this.upgradeSlotX = BlastFurnaceContainer.SLOT_UPGRADE_X - 2;
        this.upgradeSlotY = BlastFurnaceContainer.SLOT_UPGRADE_Y - 2;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;

        if (this.tile.isWorking()) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
            int i1 = this.tile.getProgressScaled(90);
            this.drawTexturedModalRect(k + 19, l + 53, 0, 167, i1 + 1, 11);
        }
    }

}
