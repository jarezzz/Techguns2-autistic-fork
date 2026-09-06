package techguns.world.dungeon.presets.specialblocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import techguns.TGBlocks;
import techguns.blocks.EnumMilitaryCrateType;
import techguns.util.MBlock;

public class SpecialBlockHandler {

    public static void applySpecialMBlocks(List<MBlock> list, ResourceLocation loottable) {

        for (int i = 0; i < list.size(); i++) {

            MBlock mb = list.get(i);

            if (mb.block == TGBlocks.MONSTER_SPAWNER) {
                list.set(i, new MBlockTGSpawner(mb));
            } else if (mb.block == Blocks.SKULL) {
                list.set(i, new MBlockSkull(mb));
            } else if (mb.block == TGBlocks.MILITARY_CRATE) {
                list.set(i, new RandomStateMBlock(mb, EnumMilitaryCrateType.values().length));
            } else if ((mb.block == Blocks.CHEST || mb.block == Blocks.TRAPPED_CHEST || isShulkerBox(mb.block)) && loottable != null) {
                list.set(i, new MBlockChestLoottable(mb.block, mb.meta, loottable));
            }

        }

    }
    // Th3_Sl1ze: mojank...
    public static boolean isShulkerBox(Block block) {
        List<Block> shulkerBoxes = new ArrayList<>(Arrays.asList(
                Blocks.BLACK_SHULKER_BOX,
                Blocks.BLUE_SHULKER_BOX,
                Blocks.BROWN_SHULKER_BOX,
                Blocks.GRAY_SHULKER_BOX,
                Blocks.CYAN_SHULKER_BOX,
                Blocks.SILVER_SHULKER_BOX,
                Blocks.GREEN_SHULKER_BOX,
                Blocks.LIGHT_BLUE_SHULKER_BOX,
                Blocks.LIME_SHULKER_BOX,
                Blocks.MAGENTA_SHULKER_BOX,
                Blocks.ORANGE_SHULKER_BOX,
                Blocks.WHITE_SHULKER_BOX,
                Blocks.YELLOW_SHULKER_BOX,
                Blocks.RED_SHULKER_BOX,
                Blocks.PINK_SHULKER_BOX,
                Blocks.PURPLE_SHULKER_BOX
        ));
        return shulkerBoxes.contains(block);
    }

}
