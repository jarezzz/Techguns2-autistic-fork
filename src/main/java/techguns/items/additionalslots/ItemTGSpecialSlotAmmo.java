package techguns.items.additionalslots;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import techguns.api.tginventory.TGSlotType;
import techguns.util.InventoryUtil;
import techguns.util.TextUtil;

public abstract class ItemTGSpecialSlotAmmo extends ItemTGSpecialSlot {

    ItemStack ammo;
    ItemStack ammoEmpty;

    public ItemTGSpecialSlotAmmo(String unlocalizedName, TGSlotType slot, int camoCount, int dur, ItemStack ammo, ItemStack ammoEmpty) {
        super(unlocalizedName, slot, camoCount, dur);
        this.ammo = ammo;
        this.ammoEmpty = ammoEmpty;
    }

    public ItemStack getAmmo() {
        return ammo;
    }

    @Override
    public void addInformation(@NotNull ItemStack stack, World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(ChatFormatting.AQUA + TextUtil.transTG("tooltip.reloads_with") + ": " + TextUtil.trans(this.ammo.getTranslationKey() + ".name"));
    }

    protected void tryReloadAndRepair(ItemStack item, EntityPlayer ply) {
        if (InventoryUtil.consumeAmmoPlayer(ply, ammo)) {
            item.setItemDamage(0);
            if (InventoryUtil.addAmmoToPlayerInventory(ply, ammoEmpty) > 0) {
                if (!ply.world.isRemote) {
                    ply.world.spawnEntity(new EntityItem(ply.world, ply.posX, ply.posY, ply.posZ, new ItemStack(ammoEmpty.getItem(), 1, ammoEmpty.getItemDamage())));
                }
            }
        }
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(@NotNull ItemStack stack, @NotNull ItemStack book) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack, net.minecraft.enchantment.@NotNull Enchantment enchantment) {
        return false;
    }
}