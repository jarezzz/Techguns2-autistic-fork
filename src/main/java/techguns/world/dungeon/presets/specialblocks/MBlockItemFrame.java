package techguns.world.dungeon.presets.specialblocks;

import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import techguns.util.MBlock;
import techguns.world.EnumLootType;
import techguns.world.structures.WorldgenStructure.BiomeColorType;

import javax.annotation.Nullable;
import java.util.Objects;

public class MBlockItemFrame extends MBlock {

    protected int facingIndex;
    protected String itemRegistryName;
    protected int itemMeta;
    protected int itemCount;
    protected NBTTagCompound itemNBT;
    protected int itemRotation;

    public MBlockItemFrame(int facingIndex, @Nullable String itemRegistryName, int itemMeta, int itemCount,
                           @Nullable String itemNBT, int itemRotation) {
        super(Blocks.AIR, 0);
        this.facingIndex = facingIndex;
        this.itemRegistryName = itemRegistryName;
        this.itemMeta = itemMeta;
        this.itemCount = itemCount;
        this.itemNBT = parseNBT(itemNBT);
        this.itemRotation = itemRotation;
    }

    public MBlockItemFrame(int facingIndex, String itemRegistryName, int itemMeta) {
        this(facingIndex, itemRegistryName, itemMeta, 1, null, 0);
    }

    public MBlockItemFrame(int facingIndex) {
        this(facingIndex, null, 0, 0, null, 0);
    }

    @Nullable
    protected static NBTTagCompound parseNBT(@Nullable String nbtString) {
        if (nbtString == null || nbtString.isEmpty()) {
            return null;
        }
        try {
            return JsonToNBT.getTagFromJson(nbtString);
        } catch (NBTException e) {
            return null;
        }
    }

    @Override
    public void setBlock(World w, MutableBlockPos pos, int rotation, EnumLootType loottype, BiomeColorType biome) {
        if (pos.getY() >= 1 && !w.isRemote) {
            EnumFacing facing = EnumFacing.byIndex(this.facingIndex);
            EnumFacing rotatedFacing = rotateFacingHorizontal(facing, rotation);

            EntityItemFrame frame = new EntityItemFrame(w, pos.toImmutable(), rotatedFacing);

            if (this.itemRegistryName != null && !this.itemRegistryName.isEmpty()) {
                Item item = Item.REGISTRY.getObject(new ResourceLocation(this.itemRegistryName));
                if (item != null && item != Items.AIR) {
                    ItemStack stack = new ItemStack(item, Math.max(1, this.itemCount), this.itemMeta);
                    if (this.itemNBT != null) {
                        stack.setTagCompound(this.itemNBT.copy());
                    }
                    frame.setDisplayedItem(stack);
                    frame.setItemRotation(this.itemRotation);
                }
            }

            w.spawnEntity(frame);
        }
    }

    protected EnumFacing rotateFacingHorizontal(EnumFacing original, int rotation) {
        if (original.getAxis() == EnumFacing.Axis.Y) {
            return original;
        }
        EnumFacing result = original;
        for (int i = 0; i < rotation; i++) {
            result = result.rotateY();
        }
        return result;
    }

    public int getFacingIndex() {
        return facingIndex;
    }

    public String getItemRegistryName() {
        return itemRegistryName;
    }

    public int getItemMeta() {
        return itemMeta;
    }

    public int getItemCount() {
        return itemCount;
    }

    public NBTTagCompound getItemNBT() {
        return itemNBT;
    }

    public int getItemRotation() {
        return itemRotation;
    }

    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) return false;
        if (!(other instanceof MBlockItemFrame o)) return false;
        return this.facingIndex == o.facingIndex
                && Objects.equals(this.itemRegistryName, o.itemRegistryName)
                && this.itemMeta == o.itemMeta
                && this.itemCount == o.itemCount
                && Objects.equals(this.itemNBT, o.itemNBT)
                && this.itemRotation == o.itemRotation;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + facingIndex;
        result = 31 * result + (itemRegistryName != null ? itemRegistryName.hashCode() : 0);
        result = 31 * result + itemMeta;
        result = 31 * result + itemCount;
        result = 31 * result + (itemNBT != null ? itemNBT.hashCode() : 0);
        result = 31 * result + itemRotation;
        return result;
    }
}