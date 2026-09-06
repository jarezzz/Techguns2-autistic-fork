package techguns.world.dungeon;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.world.dungeon.MazeDungeonPath.PathSegment;
import techguns.world.dungeon.presets.IDungeonPreset;

public interface IDungeonPath {

	int getNumSegments();

	void generatePath();

	void generateSegment(int x, int y, int z, int dir, PathSegment prev);

	void generateDungeon(World world, int posX, int posY, int posZ, IDungeonPreset preset);

	void generateNPCSpawners(World world, int posX, int posY, int posZ, IDungeonPreset preset);

	EnumFacing getEntranceRotation();

	BlockPos getStartPos();
}