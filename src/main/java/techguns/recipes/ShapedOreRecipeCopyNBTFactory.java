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

public class ShapedOreRecipeCopyNBTFactory implements IRecipeFactory {

    public static final String COPY_NBT_RECIPE = "copy_nbt";

    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        ShapedOreRecipe recipe = ShapedOreRecipe.factory(context, json);
        ShapedPrimer p = new ShapedPrimer();
        p.height = recipe.getRecipeHeight();
        p.width = recipe.getRecipeWidth();
        p.input = recipe.getIngredients();
        p.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
        return new ShapedOreRecipeCopyNBT(new ResourceLocation(Tags.MOD_ID, COPY_NBT_RECIPE), recipe.getRecipeOutput(), p);
    }

    public static class ShapedOreRecipeCopyNBT extends ShapedOreRecipe {

        public ShapedOreRecipeCopyNBT(ResourceLocation group, ItemStack result, ShapedPrimer primer) {
            super(group, result, primer);
        }

        @Override
        public @NotNull ItemStack getCraftingResult(InventoryCrafting var1) {
            int slot = 0;

            for (int i = 0; i < var1.getSizeInventory(); i++) {
                if (!var1.getStackInSlot(i).isEmpty()) {
                    if (var1.getStackInSlot(i).getItem() instanceof GenericGun) {
                        slot = i;
                    }
                }
            }

            NBTTagCompound tags = var1.getStackInSlot(slot).getTagCompound();
            NBTTagCompound newTags = null;
            if (tags != null) {
                newTags = tags.copy();
            }
            ItemStack out = super.getCraftingResult(var1);
            if (newTags != null) {
                out.setTagCompound(newTags);
            }
            return out;
        }


    }
}
