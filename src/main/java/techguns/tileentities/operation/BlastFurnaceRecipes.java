package techguns.tileentities.operation;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import techguns.tileentities.BlastFurnaceTileEnt;
import techguns.util.ItemStackOreDict;

import java.util.ArrayList;
import java.util.List;

public class BlastFurnaceRecipes {

    private static final ArrayList<BlastFurnaceRecipe> recipes = new ArrayList<>();

    public static void addRecipe(ItemStack slot1, ItemStack slot2, ItemStack output, int powerPerTick, int duration) {
        recipes.add(new BlastFurnaceRecipe(new ItemStackOreDict(slot1), slot1.getCount(), new ItemStackOreDict(slot2), slot2.getCount(), output, powerPerTick, duration));
    }

    public static void addRecipe(String slot1, int amount1, ItemStack slot2, ItemStack output, int powerPerTick, int duration) {
        recipes.add(new BlastFurnaceRecipe(new ItemStackOreDict(slot1, amount1), amount1, new ItemStackOreDict(slot2), slot2.getCount(), output, powerPerTick, duration));
    }

    public static void addRecipe(ItemStack slot1, String slot2, int amount2, ItemStack output, int powerPerTick, int duration) {
        recipes.add(new BlastFurnaceRecipe(new ItemStackOreDict(slot1), slot1.getCount(), new ItemStackOreDict(slot2, amount2), amount2, output, powerPerTick, duration));
    }

    public static void addRecipe(String slot1, int amount1, String slot2, int amount2, ItemStack output, int powerPerTick, int duration) {
        recipes.add(new BlastFurnaceRecipe(new ItemStackOreDict(slot1, amount1), amount1, new ItemStackOreDict(slot2, amount2), amount2, output, powerPerTick, duration));
    }

    public static ArrayList<BlastFurnaceRecipe> getRecipes() {
        return recipes;
    }

    public static void removeRecipesFor(ItemStack output) {
        recipes.removeIf(blastFurnaceRecipe -> blastFurnaceRecipe.output.isItemEqual(output));
    }

    public static MachineOperation getOutputFor(BlastFurnaceTileEnt tile) {
        for (BlastFurnaceRecipe recipe : recipes) {
            if (recipe.isValidInput(tile.input1.get(), tile.input2.get())) {
                return recipe.getOperationFor(tile);
            }
        }
        return null;
    }

    public static boolean hasRecipeWithInputForSlot(ItemStack item, int slot) {
        for (BlastFurnaceRecipe recipe : recipes) {
            if (slot == 0 && recipe.slot1.isEqualWithOreDict(item)) {
                return true;
            } else if (slot == 1 && recipe.slot2.isEqualWithOreDict(item)) {
                return true;
            }
        }
        return false;
    }

    public static class BlastFurnaceRecipe implements IMachineRecipe {
        public ItemStackOreDict slot1;
        public int amount1;
        public ItemStackOreDict slot2;
        public int amount2;
        public ItemStack output;
        public int powerPerTick;
        public int duration;

        public BlastFurnaceRecipe(ItemStackOreDict slot1, int amount1, ItemStackOreDict slot2, int amount2,
                                  ItemStack output, int powerPerTick, int duration) {
            super();
            this.slot1 = slot1;
            this.amount1 = amount1;
            this.slot2 = slot2;
            this.amount2 = amount2;
            this.output = output;
            this.powerPerTick = powerPerTick;
            this.duration = duration;
        }

        public boolean isValidInput(ItemStack slot1, ItemStack slot2) {
            if (this.slot1.isEqualWithOreDict(slot1) && this.slot2.isEqualWithOreDict(slot2)) {
                return slot1.getCount() >= amount1 && slot2.getCount() >= amount2;
            }
            return false;
        }

        @Override
        public List<List<ItemStack>> getItemInputs() {
            List<List<ItemStack>> inputs = new ArrayList<>();

            inputs.add(this.slot1.getItemStacks());
            inputs.add(this.slot2.getItemStacks());

            return inputs;
        }

        @Override
        public List<List<ItemStack>> getItemOutputs() {
            List<List<ItemStack>> outputs = new ArrayList<>();

            ArrayList<ItemStack> output = new ArrayList<>();
            output.add(this.output);
            outputs.add(output);
            return outputs;
        }

        public MachineOperation getOperationFor(BlastFurnaceTileEnt tile) {
            ArrayList<ItemStack> inputs = new ArrayList<>();

            ItemStack input1 = tile.input1.get().copy();
            input1.setCount(amount1);

            ItemStack input2 = tile.input2.get().copy();
            input2.setCount(amount2);

            inputs.add(input1);
            inputs.add(input2);

            ArrayList<FluidStack> fluidsIn = new ArrayList<>();

            ArrayList<ItemStack> outputs = new ArrayList<>();
            ItemStack output = this.output.copy();
            outputs.add(output);

            ArrayList<FluidStack> fluidsOut = new ArrayList<>();

            MachineOperation op = new MachineOperation(inputs, outputs, fluidsIn, fluidsOut, 1);
            op.setPowerPerTick(this.powerPerTick);
            op.setTime(this.duration);
            return op;
        }
    }

}
