package techguns.items.guns;

import java.util.HashMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IGenericGunMelee<T extends GenericGun> {

    default T setMeleeDmg(float dmgPowered, float dmgUnpowered) {
        GenericGun g = (GenericGun) this;
        g.meleeDamagePwr = dmgPowered;
        g.meleeDamageEmpty = dmgUnpowered;
        return (T) this;
    }

    default T setTool(String toolclass, int harvestLevel) {
        if (!this.getMiningLevels().containsKey("default")) {
            this.getMiningLevels().put("default", harvestLevel);
        }

        this.getMiningLevels().put(toolclass, harvestLevel);
        return (T) this;
    }

    /**
     * Call this in getStrVsBlock()
     *
     * @param itemstack
     * @param state
     * @return
     */
    default float getDigSpeed(ItemStack itemstack, IBlockState state) {
        if (this.isEffectiveToolForState(itemstack, state)) {
            return this.getEffectiveDigSpeed(itemstack);
        } else {
            return 1.0f;
        }
    }

    default boolean isEffectiveToolForState(ItemStack stack, IBlockState state) {
        GenericGun g = (GenericGun) this;
        if (g.getCurrentAmmo(stack) >= g.getMiningAmmoConsumption()) {
            String toolclass = state.getBlock().getHarvestTool(state);
            if (toolclass != null) {
                return this.getMiningLevels().containsKey(toolclass);
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * call this in getHarvetLevel
     *
     * @param stack
     * @param toolClass
     * @return
     */
    default int getToolHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player) {
        if (this.getMiningLevels() == null) {
            return -1;
        }
        if (toolClass == null) {
            return this.getMiningLevels().get("default") + getExtraMiningLevel(stack, null, player);
        } else if (this.getMiningLevels().containsKey(toolClass)) {
            return this.getMiningLevels().get(toolClass) + getExtraMiningLevel(stack, toolClass, player);
        } else {
            return -1;
        }
    }

    default int getExtraMiningLevel(ItemStack stack, String toolClass, EntityPlayer player) {
        return 0;
    }

    T setDigSpeed(float speed);

    float getEffectiveDigSpeed(ItemStack itemstack);

    HashMap<String, Integer> getMiningLevels();
}
