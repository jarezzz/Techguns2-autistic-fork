package techguns.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.world.EnumLootType;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

import java.util.Random;

public abstract class MEntity {

    protected double offsetX;
    protected double offsetY;
    protected double offsetZ;
    protected int layer;

    public MEntity() {
        this(0.5, 0.0, 0.5);
    }

    public MEntity(double offsetX, double offsetY, double offsetZ) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.layer = 1;
    }

    public abstract Entity createEntity(World world);

    public abstract String getEntityType();

    public int getPass() {
        return layer;
    }

    public MEntity setLayer(int layer) {
        this.layer = layer;
        return this;
    }

    public void spawnEntity(World world, MutableBlockPos pos, int rotation, EnumLootType lootType, BiomeColorType biome) {
        Entity entity = createEntity(world);
        if (entity != null) {
            double[] rotatedOffset = rotateOffset(offsetX, offsetZ, rotation);
            double x = pos.getX() + rotatedOffset[0];
            double y = pos.getY() + offsetY;
            double z = pos.getZ() + rotatedOffset[1];

            entity.setPosition(x, y, z);
            applyRotation(entity, rotation);
            configureEntity(entity, rotation, lootType, biome);

            world.spawnEntity(entity);
        }
    }

    protected double[] rotateOffset(double offX, double offZ, int rotation) {
        double centeredX = offX - 0.5;
        double centeredZ = offZ - 0.5;

        for (int i = 0; i < rotation; i++) {
            double temp = centeredX;
            centeredX = centeredZ;
            centeredZ = -temp;
        }

        return new double[]{centeredX + 0.5, centeredZ + 0.5};
    }

    protected void applyRotation(Entity entity, int rotation) {
        float yaw = rotation * 90.0F;
        entity.rotationYaw = yaw;
        entity.prevRotationYaw = yaw;
    }

    protected void configureEntity(Entity entity, int rotation, EnumLootType lootType, BiomeColorType biome) {
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public double getOffsetZ() {
        return offsetZ;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MEntity other = (MEntity) obj;
        return Double.compare(other.offsetX, offsetX) == 0
                && Double.compare(other.offsetY, offsetY) == 0
                && Double.compare(other.offsetZ, offsetZ) == 0;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Double.hashCode(offsetX);
        result = 31 * result + Double.hashCode(offsetY);
        result = 31 * result + Double.hashCode(offsetZ);
        return result;
    }
}