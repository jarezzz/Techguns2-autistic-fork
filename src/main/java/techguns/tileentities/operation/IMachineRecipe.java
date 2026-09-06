package techguns.tileentities.operation;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public interface IMachineRecipe {
    default List<List<ItemStack>> getItemInputs() {
        return new ArrayList<>();
    }

    default List<List<ItemStack>> getItemOutputs() {
        return new ArrayList<>();
    }

    default List<List<FluidStack>> getFluidInputs() {
        return new ArrayList<>();
    }

    default List<List<FluidStack>> getFluidOutputs() {
        return new ArrayList<>();
    }
}
