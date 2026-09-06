package techguns.tileentities.operation;

import net.minecraft.item.ItemStack;
import techguns.util.ItemStackOreDict;

import java.util.ArrayList;
import java.util.List;

public class ChargingStationRecipe implements IMachineRecipe {
    protected static ArrayList<ChargingStationRecipe> recipes = new ArrayList<>();

    //members
    public ItemStackOreDict input;
    public ItemStack output;
    public int chargeAmount;

    private ChargingStationRecipe(ItemStackOreDict input, ItemStack output, int chargeAmount) {
        super();
        this.input = input;
        this.output = output;
        this.chargeAmount = chargeAmount;
    }

    public static ArrayList<ChargingStationRecipe> getRecipes() {
        return recipes;
    }

    public static ChargingStationRecipe addRecipe(ItemStackOreDict input, ItemStack output, int chargeAmount) {
        ChargingStationRecipe rec = new ChargingStationRecipe(input, output, chargeAmount);
        recipes.add(rec);
        return rec;
    }

    public static ChargingStationRecipe getRecipeFor(ItemStack input) {
        for (ChargingStationRecipe rec : recipes) {
            if (rec.input.isEqualWithOreDict(input)) {
                return rec;
            }
        }
        return null;
    }

    @Override
    public List<List<ItemStack>> getItemInputs() {
        ArrayList<List<ItemStack>> list = new ArrayList<>();
        list.add(this.input.getItemStacks());
        return list;
    }

    @Override
    public List<List<ItemStack>> getItemOutputs() {
        ArrayList<List<ItemStack>> list = new ArrayList<>();
        ArrayList<ItemStack> output = new ArrayList<>();
        output.add(this.output);
        list.add(output);
        return list;
    }


}
