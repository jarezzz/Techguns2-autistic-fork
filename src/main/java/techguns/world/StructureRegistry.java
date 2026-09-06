package techguns.world;

import java.util.*;
import java.util.function.Supplier;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import techguns.world.structures.*;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

public class StructureRegistry {
    private static final LinkedHashMap<String, Supplier<WorldgenStructure>> structures = new LinkedHashMap<>();
    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;

        // Smallies
        register("FactoryHouseSmall", () -> new FactoryHouseSmall(8, 0, 7, 9, 5, 10).setXZSize(11, 10));
        register("FactoryHouseBig", () -> new FactoryHouseBig(23, 0, 14, 23, 9, 14).setXYZSize(23, 9, 14));
        register("SmallTrainstation", () -> new SmallTrainstation(0, 0, 0, 0, 0, 0).setXZSize(11, 12));
        register("SmallMine", () -> new SmallMine().setXZSize(17, 11));
        register("GasStation", GasStation::new);

        // Medium structures
        register("AlienBugNest", () -> new AlienBugNestStructure().setXZSize(4, 4));
        register("PoliceStation", PoliceStation::new);
        register("FactoryBig", () -> new FactoryBig(20, 0, 43, 20, 15, 43).setXYZSize(20, 15, 43));
        register("SurvivorHideout", SurvivorHideout::new);
        register("OreClusterSpike", () -> new OreClusterSpike().setXZSize(8, 8));
        register("OreClusterMeteorBasis", () -> new OreClusterMeteorBasis().setXZSize(17, 17));
        register("DesertOilCluster", () -> new DesertOilCluster().setXZSize(11, 11));

        // Big ones
        register("MilitaryBase", () -> new MilitaryBaseStructure(0, 0, 0, 0, 0, 0));
        register("Castle", CastleStructure::new);
        register("AircraftCarrier", () -> new AircraftCarrier(54, 24, 21, 54, 24, 21).setXZSize(54, 21));
        register("Train", Train::new);

        // Nether
        register("NetherAltarSmall", () -> new NetherAltarSmall().setXZSize(11, 11));
        register("NetherSoulPlatform", () -> new NetherSoulPlatform().setXZSize(11, 11));
        register("NetherLoot01", () -> new NetherLoot01().setXZSize(6, 6));
        register("NetherAcidHole", () -> new NetherAcidHole().setXZSize(9, 9));
        register("NetherOreClusterSmall", () -> new NetherOreClusterSmall().setXZSize(3, 3));
        register("NetherAltarMedium", () -> new NetherAltarMedium().setXZSize(16, 16));
        register("NetherGhastSpawner", () -> new NetherGhastSpawner().setXZSize(10, 10));
        register("NetherOreClusterMedium", () -> new NetherOreClusterMedium().setXZSize(18, 12));
        register("NetherOreClusterCastle", () -> new NetherOreClusterCastle().setXZSize(11, 11));
        register("NetherBuilding", NetherBuilding::new);
        register("NetherSubmarine", NetherSubmarine::new);

        // End
        register("EndFloatingShip", EndFloatingShip::new);
        register("EndBuilding", EndBuilding::new);
        register("EndCluster", EndCluster::new);
    }

    public static void register(String name, Supplier<WorldgenStructure> supplier) {
        structures.put(name, supplier);
    }

    public static WorldgenStructure createStructure(String name) {
        for (Map.Entry<String, Supplier<WorldgenStructure>> entry : structures.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(name)) {
                return entry.getValue().get();
            }
        }
        return null;
    }

    public static Set<String> getStructureNames() {
        return structures.keySet();
    }

    public static List<String> getMatchingNames(String input) {
        List<String> matches = new ArrayList<>();
        String lowerInput = input.toLowerCase();
        for (String name : structures.keySet()) {
            if (name.toLowerCase().startsWith(lowerInput)) {
                matches.add(name);
            }
        }
        return matches;
    }

    public static boolean isValidStructure(String name) {
        for (String key : structures.keySet()) {
            if (key.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static String getExactName(String name) {
        for (String key : structures.keySet()) {
            if (key.equalsIgnoreCase(name)) {
                return key;
            }
        }
        return name;
    }

    private static final BiomeDictionary.Type[] coldTypes = {BiomeDictionary.Type.COLD, BiomeDictionary.Type.SNOWY};
    private static final BiomeDictionary.Type[] sandTypes = {BiomeDictionary.Type.SANDY, BiomeDictionary.Type.BEACH, BiomeDictionary.Type.MESA};
    private static final BiomeDictionary.Type[] netherTypes = {BiomeDictionary.Type.NETHER};

    public static BiomeColorType getBiomeColorType(Biome biome) {
        if (isInBiomeList(coldTypes, biome)) {
            return BiomeColorType.SNOW;
        } else if (isInBiomeList(sandTypes, biome)) {
            return BiomeColorType.DESERT;
        } else if (isInBiomeList(netherTypes, biome)) {
            return BiomeColorType.NETHER;
        }
        return BiomeColorType.WOODLAND;
    }

    private static boolean isInBiomeList(BiomeDictionary.Type[] list, Biome biome) {
        for (BiomeDictionary.Type type : list) {
            if (BiomeDictionary.hasType(biome, type)) {
                return true;
            }
        }
        return false;
    }
}