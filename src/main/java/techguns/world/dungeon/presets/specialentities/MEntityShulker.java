package techguns.world.dungeon.presets.specialentities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import techguns.util.MEntity;
import techguns.world.EnumLootType;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

import java.util.Random;

public class MEntityShulker extends MEntity {

    private final EnumFacing attachFace;

    public MEntityShulker() {
        this(EnumFacing.DOWN);
    }

    public MEntityShulker(EnumFacing attachFace) {
        super(0.5, 0.0, 0.5);
        this.attachFace = attachFace;
    }

    public MEntityShulker(double offsetX, double offsetY, double offsetZ, EnumFacing attachFace) {
        super(offsetX, offsetY, offsetZ);
        this.attachFace = attachFace;
    }

    @Override
    public Entity createEntity(World world) {
        return new EntityShulker(world);
    }

    @Override
    public String getEntityType() {
        return "shulker";
    }

    @Override
    protected void configureEntity(Entity entity, int rotation, EnumLootType lootType, BiomeColorType biome) {
        if (entity instanceof EntityShulker shulker) {

            EnumFacing rotatedFace = rotateAttachFace(attachFace, rotation);
            NBTTagCompound nbt = new NBTTagCompound();
            shulker.writeEntityToNBT(nbt);
            nbt.setByte("AttachFace", (byte) rotatedFace.getIndex());
            shulker.readEntityFromNBT(nbt);
        }
    }

    private EnumFacing rotateAttachFace(EnumFacing face, int rotation) {
        if (face.getAxis() == EnumFacing.Axis.Y) {
            return face;
        }
        EnumFacing result = face;
        for (int i = 0; i < rotation; i++) {
            result = result.rotateY();
        }
        return result;
    }

    public EnumFacing getAttachFace() {
        return attachFace;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof MEntityShulker other)) return false;
        return attachFace == other.attachFace;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + attachFace.hashCode();
        return result;
    }
}