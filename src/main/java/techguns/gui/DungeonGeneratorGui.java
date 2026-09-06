package techguns.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import techguns.TGPackets;
import techguns.Tags;
import techguns.gui.containers.DungeonGeneratorContainer;
import techguns.packets.PacketGuiButtonClick;
import techguns.tileentities.DungeonGeneratorTileEnt;
import techguns.world.dungeon.DungeonTemplate;

import java.io.IOException;

public class DungeonGeneratorGui extends OwnedTileEntGui {

    public static final ResourceLocation texture = new ResourceLocation(Tags.MOD_ID, "textures/gui/dungeon_scanner_gui.png");// new

    protected DungeonGeneratorTileEnt tileEnt;

    private String textInput = "";

    public DungeonGeneratorGui(InventoryPlayer ply, DungeonGeneratorTileEnt tile) {
        super(new DungeonGeneratorContainer(ply, tile), tile);
        this.tex = texture;
        this.tileEnt = tile;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int x = 0;
        int y = 0;
        int color = 4210752; //0xff101010;

        this.fontRenderer.drawString(textInput, x + 6, y + 70, 0xffffffff);

        //existing templates
        int dy = 0;
        for (String template : DungeonTemplate.dungeonTemplates.keySet()) {
            this.fontRenderer.drawString(template, x + 75, y + 18 + (dy), color);
            dy += 8;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        //super.keyTyped(typedChar, keyCode);

        if (keyCode == 1) {
            this.mc.player.closeScreen();
        } else {
            if (Character.isLetterOrDigit(typedChar) && textInput.length() < 10) {
                textInput += typedChar;
            } else if (typedChar == '\b') {
                if (!textInput.isEmpty()) textInput = textInput.substring(0, textInput.length() - 1);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;

        int xpos = 75;
        int width = 93;

        int dy = 0;
        for (String template : DungeonTemplate.dungeonTemplates.keySet()) {
            /*this.fontRenderer.drawString(template, k+75, l+18+(dy), color);*/
            if (isInRect(mouseX, mouseY, k + xpos, l + 18 + dy, width, 8)) {
                this.textInput = template;
                break;
            }
            dy += 8;
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        drawRect(k + 5, l + 69, k + 69, l + 79, 0xff000000);

        drawRect(k + 74, l + 17, k + 169, l + 158, 0xff888888);

        int xpos = 75;
        int width = 93;

        //existing templates
        int dy = 0;
        for (String template : DungeonTemplate.dungeonTemplates.keySet()) {
            if (isInRect(mouseX, mouseY, k + xpos, l + 18 + dy, width, 8)) {
                drawRect(k + xpos, l + 18 + dy, k + xpos + width, l + 18 + dy + 8, 0xFFA0A0A0);
                break;
            }
            dy += 8;
        }
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        if (!textInput.isEmpty() && (guibutton.id == DungeonGeneratorTileEnt.BUTTON_ID_GENERATE)) {
            TGPackets.wrapper.sendToServer(new PacketGuiButtonClick(this.ownedTile, guibutton.id, textInput));

        } else {

            super.actionPerformed(guibutton);
        }
    }

    @Override
    public void initGui() {
        super.initGui();

        this.buttonList.add(new GuiButtonExt(DungeonGeneratorTileEnt.BUTTON_ID_GHOST, this.guiLeft + 5, this.guiTop + 5, 12, 12, "G"));

        this.buttonList.add(new GuiButtonExt(DungeonGeneratorTileEnt.BUTTON_ID_GENERATE, this.guiLeft + 5, this.guiTop + 81, 64, 12, "generate"));


        this.buttonList.add(new GuiButtonExt(DungeonGeneratorTileEnt.BUTTON_ID_CLEAR, this.guiLeft + 5, this.guiTop + 149, 12, 12, "C"));
    }
}
