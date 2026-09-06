package techguns.recipes;

import com.google.gson.JsonObject;

import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import org.jetbrains.annotations.NotNull;

public class OreDictIngredientHardenedGlass implements IIngredientFactory {

    @Override
    public @NotNull Ingredient parse(JsonContext context, JsonObject json) {
        if (OreDictionary.doesOreNameExist("blockGlassHardened")) {
            return new OreIngredient("blockGlassHardened");
        }
        return new OreIngredient("blockGlass");
    }

}
