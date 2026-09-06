package techguns.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import techguns.TGOreClusters.OreCluster;
import techguns.util.TextUtil;
import techguns.*;

public class BlockOreCluster<T extends Enum<T> & IEnumOreClusterType> extends GenericBlockMetaEnum<T> {

	public BlockOreCluster(String name, Material mat, Class<T> clazz) {
		super(name, mat, clazz);
		this.setBlockUnbreakable();
		this.setResistance(6000000.0F);
	}

    @Override
	public @NotNull BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
    public int quantityDropped(@NotNull Random random)
    {
        return 0;
    }

	@Override
    public @NotNull Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune)
    {
        return Items.AIR;
    }

	@Override
	public boolean shouldAutoGenerateJsonForEnum() {
		return false;
	}

	@Override
	public void addInformation(@NotNull ItemStack stack, World player, @NotNull List<String> tooltip, @NotNull ITooltipFlag advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		
		IBlockState state = this.getStateFromMeta(stack.getItemDamage());
		
		OreCluster ore = Techguns.orecluster.getClusterForType(state.getValue(TYPE));
		if(ore!=null) {
			tooltip.add(TextUtil.trans("techguns.orecluster.mininglevel")+": "+ore.getMininglevel());
			tooltip.add(TextUtil.trans("techguns.orecluster.powermult")+": x"+String.format("%.1f",ore.getMultiplier_power()));
			tooltip.add(TextUtil.trans("techguns.orecluster.amountmult")+": x"+String.format("%.1f",ore.getMultiplier_amount()));
		}
	}
}
