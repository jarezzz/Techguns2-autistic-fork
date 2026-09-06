package techguns.world.dungeon.presets.specialentities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.world.World;
import techguns.util.MEntity;
import techguns.world.EnumLootType;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

import java.util.Random;

public class MEntityEndCrystal extends MEntity {

    private final boolean showBottom;

    public MEntityEndCrystal() {
        this(true);
    }

    public MEntityEndCrystal(boolean showBottom) {
        super(0.5, 0.0, 0.5);
        this.showBottom = showBottom;
    }

    public MEntityEndCrystal(double offsetX, double offsetY, double offsetZ, boolean showBottom) {
        super(offsetX, offsetY, offsetZ);
        this.showBottom = showBottom;
    }

    @Override
    public Entity createEntity(World world) {
        return new EntityEnderCrystal(world);
    }

    @Override
    public String getEntityType() {
        return "end_crystal";
    }

    @Override
    protected void configureEntity(Entity entity, int rotation, EnumLootType lootType, BiomeColorType biome) {
        if (entity instanceof EntityEnderCrystal crystal) {
            crystal.setShowBottom(showBottom);
        }
    }

    public boolean isShowBottom() {
        return showBottom;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof MEntityEndCrystal other)) return false;
        return showBottom == other.showBottom;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + (showBottom ? 1 : 0);
    }
}