package techguns.tileentities.operation;

import net.minecraft.item.ItemStack;
import techguns.util.ItemStackOreDict;

import java.util.ArrayList;
import java.util.List;

public class MetalPressRecipes {

    private static final ArrayList<MetalPressRecipe> recipes = new ArrayList<>();

    public static void addRecipe(ItemStack input1, ItemStack input2, ItemStack output, boolean swap) {
        recipes.add(new MetalPressRecipe(new ItemStackOreDict(input1), new ItemStackOreDict(input2), swap, output));
    }

    public static void addRecipe(String input1, String input2, ItemStack output, boolean swap) {
        recipes.add(new MetalPressRecipe(new ItemStackOreDict(input1), new ItemStackOreDict(input2), swap, output));
    }

    public static void addRecipe(String input1, ItemStack input2, ItemStack output, boolean swap) {
        recipes.add(new MetalPressRecipe(new ItemStackOreDict(input1), new ItemStackOreDict(input2), swap, output));
    }

    public static void addRecipe(ItemStack input1, String input2, ItemStack output, boolean swap) {
        recipes.add(new MetalPressRecipe(new ItemStackOreDict(input1), new ItemStackOreDict(input2), swap, output));
    }

    public static void addRecipe(ItemStack input1, ItemStack input2, ItemStack output, boolean swap, int steamCost, int requiredPressure) {
        recipes.add(new MetalPressRecipe(new ItemStackOreDict(input1), new ItemStackOreDict(input2), swap, output, steamCost, requiredPressure));
    }

    public static void addRecipe(String input1, String input2, ItemStack output, boolean swap, int steamCost, int requiredPressure) {
        recipes.add(new MetalPressRecipe(new ItemStackOreDict(input1), new ItemStackOreDict(input2), swap, output, steamCost, requiredPressure));
    }

    public static void addRecipe(ItemStackOreDict input1, ItemStackOreDict input2, ItemStack output, boolean swap, int steamCost, int requiredPressure) {
        recipes.add(new MetalPressRecipe(input1, input2, swap, output, steamCost, requiredPressure));
    }

    public static ItemStack getOutputFor(ItemStack slot1, ItemStack slot2) {
        MetalPressRecipe recipe = getRecipeForInputs(slot1, slot2);
        return recipe != null ? recipe.output : ItemStack.EMPTY;
    }

    public static MetalPressRecipe getRecipeForInputs(ItemStack slot1, ItemStack slot2) {
        for (MetalPressRecipe recipe : recipes) {
            if (recipe.isValidInput(slot1, slot2)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean hasRecipeUsing(ItemStack item) {
        for (MetalPressRecipe recipe : recipes) {
            if (recipe.isItemPartOfRecipe(item)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<MetalPressRecipe> getRecipes() {
        return recipes;
    }

    public static class MetalPressRecipe implements IMachineRecipe {
        public final ItemStackOreDict slot1;
        public final ItemStackOreDict slot2;
        public final int input1Count;
        public final int input2Count;
        public final boolean allowSwap;
        public final ItemStack output;
        public final int steamCost;
        public final int requiredPressure;

        public MetalPressRecipe(ItemStackOreDict slot1, ItemStackOreDict slot2,
                                boolean allowSwap, ItemStack output) {
            this(slot1, slot2, allowSwap, output, 0, 0);
        }

        public MetalPressRecipe(ItemStackOreDict slot1, ItemStackOreDict slot2,
                                boolean allowSwap, ItemStack output, int steamCost, int requiredPressure) {
            this.slot1 = slot1;
            this.slot2 = slot2;
            this.allowSwap = allowSwap;
            this.output = output;
            this.input1Count = slot1.stackSize;
            this.input2Count = slot2.stackSize;
            this.steamCost = Math.max(0, steamCost);
            this.requiredPressure = Math.max(0, requiredPressure);
        }

        public boolean isItemPartOfRecipe(ItemStack item) {
            return this.slot1.isEqualWithOreDict(item) || this.slot2.isEqualWithOreDict(item);
        }

        public boolean requiresSteam() {
            return this.steamCost > 0 && this.requiredPressure > 0;
        }

        //Returns if this 2 itemstacks are a valid input for this recipe;

        public boolean isValidInput(ItemStack slot1, ItemStack slot2) {
            return (this.slot1.isEqualWithOreDict(slot1) && slot1.getCount() >= input1Count &&
                    this.slot2.isEqualWithOreDict(slot2) && slot2.getCount() >= input2Count)
                    || (allowSwap && this.slot1.isEqualWithOreDict(slot2) && slot2.getCount() >= input1Count &&
                    this.slot2.isEqualWithOreDict(slot1) && slot1.getCount() >= input2Count);
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


    }
}

