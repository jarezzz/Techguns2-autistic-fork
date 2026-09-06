package techguns.plugins.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import techguns.util.ItemStackOreDict;

public class TGCraftTweakerHelper {
	public static String getItemNames(IIngredient ingr){
		boolean first=true;
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(IItemStack st: ingr.getItems()){
			if(!first){
				sb.append(",");
			} else {
				first=false;
			}
			sb.append(st.getName());
		}
		sb.append("]");
		return sb.toString();
	}

	public static ItemStackOreDict toItemStackOreDict(IItemStack stack) {
		ItemStack itemStack = CraftTweakerMC.getItemStack(stack);
		if (!itemStack.isEmpty() && itemStack.hasTagCompound()) {
			return new ItemStackOreDict(itemStack.copy());
		}
		return new ItemStackOreDict(itemStack);
	}
	
	public static ItemStackOreDict toItemStackOreDict(String oreDictName){
		return new ItemStackOreDict(oreDictName);
	}
}
