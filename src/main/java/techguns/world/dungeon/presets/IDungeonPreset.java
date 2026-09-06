package techguns.world.dungeon.presets;

import techguns.tileentities.TGSpawnerTileEnt;
import techguns.world.dungeon.DungeonSegment;
import techguns.world.dungeon.IDungeonPath;
import techguns.world.dungeon.MazeDungeonPath;
import techguns.world.dungeon.TemplateSegment.SegmentType;

import java.util.Random;

public interface IDungeonPreset {
	
	//Dungeon Presets
	IDungeonPreset PRESET_TECHFORTRESS = new PresetTechFortress();
	IDungeonPreset PRESET_CASTLE = new PresetCastle();
	IDungeonPreset PRESET_NETHER = new PresetNetherDungeonUnderground();
	

	DungeonSegment getSegment(SegmentType type, int y, int yMin, int yMax, boolean isSegmentAbove, boolean isSegmentBelow, int seed);
	
	int getSizeXZ();
	int getSizeY();
	
	//NPC stuff
	
	/**
	 * NPC spawners per number of dungeon segments.
	 */
	default float getSpawnDensity() {
		return 0.1f;
	}
	
	/**
	 * The floor height of the dungeon segments
	 */
	default int getSpawnYOffset() {
		return 1;
	}
	
	default IDungeonPath getDungeonPath(int sX, int sY, int sZ, Random rand) {
		return new MazeDungeonPath(sX, sY, sZ, rand);
	}
	
	void initDungeonPath(IDungeonPath path);

	void initSpawner(TGSpawnerTileEnt spawner);
}
