package techguns.client.render;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class RenderAdditionalSlotSharedItem extends RenderAdditionalSlotItem {

    private final HashMap<Integer, RenderAdditionalSlotItem> renderMap = new HashMap<>();

    public RenderAdditionalSlotSharedItem() {
        super(null, null);
    }

    @Override
    public void render(ItemStack slot, EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch,
                       float scale, RenderPlayer renderplayer) {

        Integer damageValue = slot.getItemDamage();

        RenderAdditionalSlotItem render = renderMap.get(damageValue);
        if (render != null) {
            render.render(slot, player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, renderplayer);
        }
    }

    public void addRenderForSharedItem(Integer dmgVal, RenderAdditionalSlotItem render) {
        this.renderMap.put(dmgVal, render);
    }
}
