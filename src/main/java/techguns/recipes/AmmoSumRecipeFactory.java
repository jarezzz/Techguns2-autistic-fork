package techguns.recipes;

import com.google.gson.JsonObject;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.jetbrains.annotations.NotNull;
import techguns.*;
import techguns.items.guns.GenericGun;

public class AmmoSumRecipeFactory implements IRecipeFactory {

    public static final String AMMO_SUM_RECIPE = "ammo_sum_recipe";
    protected static final ResourceLocation GROUP = new ResourceLocation(Tags.MOD_ID, AMMO_SUM_RECIPE);

    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        ShapedOreRecipe rec = ShapedOreRecipe.factory(context, json);

        ShapedPrimer primer = new ShapedPrimer();
        primer.height = rec.getRecipeHeight();
        primer.width = rec.getRecipeWidth();
        primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
        primer.input = rec.getIngredients();

        return new AmmoSumRecipe(GROUP, rec.getRecipeOutput(), primer);
    }

    public static class AmmoSumRecipe extends ShapedOreRecipe {
        public AmmoSumRecipe(ResourceLocation group, ItemStack result, ShapedPrimer primer) {
            super(group, result, primer);
        }

        @Override
        public @NotNull ItemStack getCraftingResult(InventoryCrafting var1) {
            int ammoSum = 0;

            for (int i = 0; i < var1.getSizeInventory(); i++) {
                if (!var1.getStackInSlot(i).isEmpty()) {
                    if (var1.getStackInSlot(i).getItem() instanceof GenericGun g) {
                        ammoSum += g.getCurrentAmmo(var1.getStackInSlot(i));
                    }
                }
            }
            ItemStack out = super.getCraftingResult(var1);
            NBTTagCompound tags = out.getTagCompound();
            tags.setShort("ammo", (short) ammoSum);

            return out;
        }


    }
}
