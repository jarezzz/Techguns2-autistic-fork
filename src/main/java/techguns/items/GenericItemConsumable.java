package techguns.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class GenericItemConsumable extends GenericItem {

    /**
     * Number of ticks to run while 'EnumAction'ing until result.
     */
    public final int itemUseDuration;
    protected boolean ignoreHunger = true;

    public GenericItemConsumable(String name, int useDuration) {
        super(name);
        this.itemUseDuration = useDuration;
    }

    public GenericItemConsumable(String name, int useDuaration, boolean addToItemList) {
        super(name, addToItemList);
        this.itemUseDuration = useDuaration;
    }


    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public @NotNull ItemStack onItemUseFinish(@NotNull ItemStack stack, @NotNull World worldIn, @NotNull EntityLivingBase entityLiving) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entityLiving;
            //entityplayer.getFoodStats().addStats(this, stack);
            worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, this.getConsumedSound(), SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            this.onConsumed(stack, worldIn, entityplayer);
            entityplayer.addStat(StatList.getObjectUseStats(this));

            if (entityplayer instanceof EntityPlayerMP) {
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) entityplayer, stack);
            }
        }

        stack.shrink(1);
        return stack;
    }

    protected SoundEvent getConsumedSound() {
        return SoundEvents.ENTITY_PLAYER_BURP;
    }

    protected void onConsumed(ItemStack stack, World worldIn, EntityPlayer player) {

    }


    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(@NotNull ItemStack stack) {
        return this.itemUseDuration;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public @NotNull EnumAction getItemUseAction(@NotNull ItemStack stack) {
        return EnumAction.EAT;
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public @NotNull ActionResult<ItemStack> onItemRightClick(@NotNull World worldIn, EntityPlayer playerIn, @NotNull EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.canEat(this.ignoreHunger)) {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        } else {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
    }

}
