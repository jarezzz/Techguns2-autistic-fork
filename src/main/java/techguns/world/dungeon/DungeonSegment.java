package techguns.world.dungeon;

import java.io.*;

import net.minecraft.world.World;
import techguns.tileentities.DungeonScannerTileEnt;
import techguns.world.Structure;
import techguns.world.dungeon.TemplateSegment.SegmentType;

/**
 * An 'instance' of a TemplateSegment for a specific DungeonTemplate
 */
public class DungeonSegment implements Serializable {

    private static final long serialVersionUID = 1L + 1;

    public transient DungeonTemplate template;
    public SegmentType type;
    public Structure structure;


    public DungeonSegment(DungeonTemplate template, SegmentType type) {
        this.template = template;
        this.type = type;
    }


    /**
     * x, y, z = World position of Scanner TileEnt
     */
    public void scanBlocks(World world, int posX, int posY, int posZ) {
        TemplateSegment segment = TemplateSegment.templateSegments.get(type);

        int x = posX + DungeonScannerTileEnt.SPACING + segment.col * (template.sizeXZ + DungeonScannerTileEnt.SPACING);
        int z = posZ + DungeonScannerTileEnt.SPACING + segment.row * (template.sizeXZ + DungeonScannerTileEnt.SPACING);

        this.structure = Structure.scanBlocks(world, x, posY, z, template.sizeXZ, template.sizeY * segment.sizeY, template.sizeXZ);
    }


    //Place Segment in template position
    public void placeTemplateSegment(World world, int posX, int posY, int posZ, int rotation) {
        TemplateSegment segment = TemplateSegment.templateSegments.get(type);

        int x = posX + DungeonScannerTileEnt.SPACING + segment.col * (template.sizeXZ + DungeonScannerTileEnt.SPACING);
        int z = posZ + DungeonScannerTileEnt.SPACING + segment.row * (template.sizeXZ + DungeonScannerTileEnt.SPACING);

        this.structure.placeBlocks(world, x, posY, z, rotation);
    }

    //place at position in dungeon
    public void placeSegment(World world, int posX, int posY, int posZ, int rotation) {
        this.structure.placeBlocks(world, posX, posY, posZ, rotation);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(template.getName());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String templateName = in.readUTF();
        this.template = DungeonTemplate.dungeonTemplates.get(templateName);
    }
}
