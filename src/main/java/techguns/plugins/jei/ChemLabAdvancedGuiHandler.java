package techguns.plugins.jei;

import mezz.jei.api.gui.IAdvancedGuiHandler;
import net.minecraftforge.fluids.FluidStack;
import techguns.gui.ChemLabGui;
import techguns.gui.TGBaseGui;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class ChemLabAdvancedGuiHandler implements IAdvancedGuiHandler<ChemLabGui> {

    @Nonnull
    @Override
    public Class<ChemLabGui> getGuiContainerClass() {
        return ChemLabGui.class;
    }

    @Nullable
    @Override
    public List<Rectangle> getGuiExtraAreas(@Nonnull ChemLabGui gui) {
        return Collections.emptyList();
    }

    @Nullable
    @Override
    public Object getIngredientUnderMouse(@Nonnull ChemLabGui gui, int mouseX, int mouseY) {
        int guiLeft = (gui.width - gui.getXSize()) / 2;
        int guiTop = (gui.height - gui.getYSize()) / 2;

        int mx = mouseX - guiLeft;
        int my = mouseY - guiTop;

        if (TGBaseGui.isInRect(mx, my, ChemLabGui.INPUT_TANK_X, ChemLabGui.TANK_Y - 1, ChemLabGui.TANK_W + 1, ChemLabGui.TANK_H + 1)) {
            FluidStack fluid = gui.getTile().inputTank.getFluid();
            if (fluid != null) {
                return fluid;
            }
        }

        if (TGBaseGui.isInRect(mx, my, ChemLabGui.OUTPUT_TANK_X, ChemLabGui.TANK_Y - 1, ChemLabGui.TANK_W + 1, ChemLabGui.TANK_H + 1)) {
            return gui.getTile().outputTank.getFluid();
        }

        return null;
    }
}