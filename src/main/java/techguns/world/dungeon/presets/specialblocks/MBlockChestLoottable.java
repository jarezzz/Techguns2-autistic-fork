package techguns.world.dungeon.presets.specialblocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.util.MBlock;

public class MBlockChestLoottable extends MBlock {

    protected ResourceLocation loottable;

    public MBlockChestLoottable(Block block, int meta, ResourceLocation loottable) {
        super(block, meta);
        this.loottable = loottable;
        this.hasTileEntity = true;
    }

    @Override
    public void tileEntityPostPlacementAction(World w, IBlockState state, BlockPos p, int rotation) {
        TileEntity tile = w.getTileEntity(p);
        if (tile instanceof TileEntityChest chest) {
            chest.setLootTable(this.loottable, w.rand.nextLong());
        } else if(tile instanceof TileEntityShulkerBox box) {
            box.setLootTable(this.loottable, w.rand.nextLong());
        }
    }

}
