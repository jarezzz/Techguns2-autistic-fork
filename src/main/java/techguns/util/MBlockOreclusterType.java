package techguns.util;

import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.TGBlocks;
import techguns.blocks.EnumOreClusterType;
import techguns.world.EnumLootType;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

import java.util.Random;

public class MBlockOreclusterType extends MultiMMBlockIndexRoll {

    float oreChance;

    MBlock[] oreMBlocks;

    public MBlockOreclusterType(EnumOreClusterType[] types, int[] weights, float oreChance, MBlock[] ores) {
        super(fillMBlocks(types), weights);
        this.oreChance = oreChance;
        this.oreMBlocks = ores;
    }

    protected static MBlock[] fillMBlocks(EnumOreClusterType[] types) {
        MBlock[] mBlocks = new MBlock[types.length];
        int i = -1;
        for (EnumOreClusterType o : types) {
            mBlocks[++i] = new MBlock(TGBlocks.ORE_CLUSTER.getDefaultState().withProperty(TGBlocks.ORE_CLUSTER.TYPE, o));
        }
        return mBlocks;
    }

    @Override
    public void setBlock(World w, MutableBlockPos pos, int rotation, EnumLootType loottype, BiomeColorType biome, int index, Random rnd) {
        if (oreChance > 0 && oreMBlocks != null && rnd.nextFloat() < oreChance) {
            this.oreMBlocks[index].setBlock(w, pos, rotation, loottype, biome);
        } else {
            this.mBlocks[index].setBlock(w, pos, rotation, loottype, biome);
        }
    }

}
