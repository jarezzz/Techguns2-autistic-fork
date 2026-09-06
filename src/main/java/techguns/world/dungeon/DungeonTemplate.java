package techguns.world.dungeon;

import java.io.*;
import java.util.HashMap;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import techguns.world.dungeon.TemplateSegment.SegmentType;

public class DungeonTemplate implements Serializable {

    private static final long serialVersionUID = 1L + 1;

    //local only for testing
    public static final String SCAN_DIR = "./templates/";

    private static final String DUNGEON_TEMPLATE_DIR = "/assets/techguns/dungeons/";
    private static final String[] TEMPLATE_LIST = new String[]{"ncdung1", "nclower1", "ncmid1", "ncupper1", "nctop1", "ncroof1", //castle
            "nether0", "nether1", "nether_b0", "nether_b1"}; //nether castle

    protected ResourceLocation LOOTTABLE = null;

    public static HashMap<String, DungeonTemplate> dungeonTemplates = new HashMap<>();

    public int sizeXZ;
    public int sizeY;

    public HashMap<SegmentType, DungeonSegment> segments = new HashMap<>();

    public String name;

    public static void init() {
        loadTemplates();
    }

    public void applySpecialMBlocks() {
        this.segments.forEach((key, value) -> value.structure.applySpecialMBlocks(LOOTTABLE));
    }

    public DungeonTemplate setLoottable(ResourceLocation loottable) {
        LOOTTABLE = loottable;
        this.applySpecialMBlocks();
        return this;
    }

    public DungeonTemplate(int sizeXZ, int sizeY) {
        super();
        this.sizeXZ = sizeXZ;
        this.sizeY = sizeY;
    }

    public void placeTemplate(World world, int posX, int posY, int posZ) {
        for (DungeonSegment segment : segments.values()) {
            if (segment.template == null) segment.template = this;
            segment.placeTemplateSegment(world, posX, posY, posZ, 0);
        }
    }

    public static DungeonTemplate scanTemplate(World world, int posX, int posY, int posZ, int sizeXZ, int sizeY, String name) {
        DungeonTemplate template = new DungeonTemplate(sizeXZ, sizeY);
        template.name = name;

        for (SegmentType type : TemplateSegment.templateSegments.keySet()) {
            DungeonSegment segment = new DungeonSegment(template, type);
            segment.scanBlocks(world, posX, posY, posZ);
            template.segments.put(type, segment);
        }

        dungeonTemplates.put(name, template);

        return template;
    }

    public static void loadTemplates() {

        for (String filename : TEMPLATE_LIST) {
            String path = DUNGEON_TEMPLATE_DIR + filename + ".ser";
            try {
                InputStream is = DungeonTemplate.class.getResourceAsStream(path);
                ObjectInputStream ois = new ObjectInputStream(is);
                DungeonTemplate template = (DungeonTemplate) ois.readObject();
                template.name = filename;
                template.applySpecialMBlocks();

                dungeonTemplates.put(filename, template);

                ois.close();
                is.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName() {
        return name;
    }

}
