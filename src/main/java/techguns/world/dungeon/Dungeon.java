package techguns.world.dungeon;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import techguns.world.dungeon.presets.IDungeonPreset;

public class Dungeon {

	private static final int GENERATE_ATTEMPTS = 5;

	IDungeonPreset preset;

	//maximum block extents
	int maxSizeX;
	int maxSizeY;
	int maxSizeZ;

	//number of segments, NOT blocks
	int sX;
	int sY;
	int sZ;

	public EnumFacing startRotation;
	public BlockPos startPos;

	public Dungeon(IDungeonPreset preset) { //TODO: DungeonLayout
		this.preset = preset;
	}

	public void generate(World world, int posX, int posY, int posZ, int maxSizeX, int maxSizeY, int maxSizeZ) {

		this.maxSizeX = maxSizeX;
		this.maxSizeY = maxSizeY;
		this.maxSizeZ = maxSizeZ;

		this.sX = maxSizeX / preset.getSizeXZ();
		this.sY = maxSizeY / preset.getSizeY();
		this.sZ = maxSizeZ / preset.getSizeXZ();

		//-- create path --

		IDungeonPath path = null;
		for (int i = 0; i < GENERATE_ATTEMPTS; i++) {
			IDungeonPath p = preset.getDungeonPath(sX, sY, sZ, world.rand);
			preset.initDungeonPath(p);
			p.generatePath();
			if (path == null || p.getNumSegments() > path.getNumSegments()) {
				path = p;
			}
		}

		path.generateDungeon(world, posX, posY, posZ, preset);

		path.generateNPCSpawners(world, posX, posY, posZ, preset);

		this.startPos = path.getStartPos();
		this.startRotation = path.getEntranceRotation();

	}

}
