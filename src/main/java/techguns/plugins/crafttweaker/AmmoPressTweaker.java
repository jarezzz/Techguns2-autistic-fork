package techguns.plugins.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techguns.tileentities.operation.AmmoPressBuildPlans;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.techguns.AmmoPress")
public class AmmoPressTweaker {

	private AmmoPressTweaker(){}

	@ZenMethod
	public static void addMetal1(IIngredient input){
		CraftTweakerAPI.apply(new addInputAction(input, (byte)0));
	}
	
	@ZenMethod
	public static void addMetal2(IIngredient input){
		CraftTweakerAPI.apply(new addInputAction(input, (byte)1));
	}
	
	@ZenMethod
	public static void addPowder(IIngredient input){
		CraftTweakerAPI.apply(new addInputAction(input, (byte)2));
	}
	
	@ZenMethod
	public static void removeMetal1(IIngredient input){
		CraftTweakerAPI.apply(new removeInputAction(input, (byte)0));
	}
	
	@ZenMethod
	public static void removeMetal2(IIngredient input){
		CraftTweakerAPI.apply(new removeInputAction(input, (byte)1));
	}
	
	@ZenMethod
	public static void removePowder(IIngredient input){
		CraftTweakerAPI.apply(new removeInputAction(input, (byte)2));
	}
	

	private static void addToList(IIngredient item, ArrayList<ItemStack> list){
		List<IItemStack> items = item.getItems();

		for (IItemStack iItemStack : items) {
			ItemStack it = CraftTweakerMC.getItemStack(iItemStack);
			if (it != null) {
				list.add(it);
			}
		}
	}

	private static void removeFromList(IIngredient item, ArrayList<ItemStack> list){
		List<IItemStack> items = item.getItems();

		for (IItemStack iItemStack : items) {
			ItemStack it = CraftTweakerMC.getItemStack(iItemStack);
			list.removeIf(input -> OreDictionary.itemMatches(it, input, false));
		}
	}

	private static String getSlotName(byte type){
        return switch (type) {
            case 1 -> "Metal2";
            case 2 -> "Powder";
            default -> "Metal1";
        };
	}
	
	private static class addInputAction implements IAction
	{

		IIngredient input;
		ArrayList<ItemStack> list;
		byte type;
		
		public addInputAction(IIngredient input, byte type) {
			super();
			this.input = input;
			this.type=type;
			switch(type){
				case 1:
					this.list = AmmoPressBuildPlans.metal2;
					break;
				case 2:
					this.list = AmmoPressBuildPlans.powder;
					break;
				case 0:
				default:
					this.list = AmmoPressBuildPlans.metal1;
					break;
			}
		}

		@Override
		public void apply() {
			addToList(input, list);
		}

		@Override
		public String describe() {
			return "Add "+TGCraftTweakerHelper.getItemNames(input)+" to AmmoPressSlot: "+getSlotName(type);
		}

	
		
	}
	
	private static class removeInputAction implements IAction
	{

		IIngredient input;
		ArrayList<ItemStack> list;
		byte type;
		
		public removeInputAction(IIngredient input, byte type) {
			super();
			this.input = input;
			this.type=type;
			switch(type){
				case 1:
					this.list = AmmoPressBuildPlans.metal2;
					break;
				case 2:
					this.list = AmmoPressBuildPlans.powder;
					break;
				case 0:
				default:
					this.list = AmmoPressBuildPlans.metal1;
					break;
			}
		}

		@Override
		public void apply() {
			removeFromList(input, list);
		}

	
		@Override
		public String describe() {
			return "Remove "+TGCraftTweakerHelper.getItemNames(input)+" to AmmoPressSlot: "+getSlotName(type);
		}
		
	}
	
}
