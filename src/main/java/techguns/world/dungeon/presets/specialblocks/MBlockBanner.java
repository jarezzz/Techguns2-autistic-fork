package techguns.world.dungeon.presets.specialblocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.util.MBlock;

public class MBlockBanner extends MBlock {

    protected int baseColor;
    protected NBTTagList patterns;

    public MBlockBanner(Block block, int meta, int baseColor, NBTTagList patterns) {
        super(block, meta);
        this.baseColor = baseColor;
        this.patterns = patterns != null ? patterns.copy() : new NBTTagList();
        this.hasTileEntity = true;
    }

    public MBlockBanner(boolean standing, int meta, int baseColor, NBTTagList patterns) {
        this(standing ? Blocks.STANDING_BANNER : Blocks.WALL_BANNER, meta, baseColor, patterns);
    }

    @Override
    public void tileEntityPostPlacementAction(World w, IBlockState state, BlockPos p, int rotation) {
        TileEntity tile = w.getTileEntity(p);
        if (tile instanceof TileEntityBanner bannerTile) {

            NBTTagCompound nbt = bannerTile.writeToNBT(new NBTTagCompound());
            nbt.setInteger("Base", this.baseColor);
            if (this.patterns != null && !this.patterns.isEmpty()) {
                nbt.setTag("Patterns", this.patterns.copy());
            }
            bannerTile.readFromNBT(nbt);
            bannerTile.markDirty();
        }
    }

    public int getBaseColor() {
        return baseColor;
    }

    public NBTTagList getPatterns() {
        return patterns;
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) return false;
        if (!(other instanceof MBlockBanner otherBanner)) return false;
        return this.baseColor == otherBanner.baseColor
                && nbtListEquals(this.patterns, otherBanner.patterns);
    }

    private boolean nbtListEquals(NBTTagList a, NBTTagList b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.equals(b);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + baseColor;
        result = 31 * result + (patterns != null ? patterns.hashCode() : 0);
        return result;
    }

    public static NBTTagList createPatterns(Object... args) {
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < args.length; i += 2) {
            String pattern = (String) args[i];
            int color = (Integer) args[i + 1];
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("Pattern", pattern);
            tag.setInteger("Color", color);
            list.appendTag(tag);
        }
        return list;
    }
}