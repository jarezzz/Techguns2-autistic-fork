package techguns.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;
import techguns.TGBlocks;
import techguns.blocks.machines.BasicMachine;
import techguns.tileentities.operation.ItemStackHandlerPlus;
import techguns.util.InventoryUtil;

public class BasicInventoryTileEnt extends BasicTGTileEntity {

    public ItemStackHandlerPlus inventory;

    protected boolean contentsChanged = true;

    public BasicInventoryTileEnt(int inventorySize, boolean hasRotation) {
        super(hasRotation);
        this.inventory = new ItemStackHandlerPlus(inventorySize);
    }

    public ItemStackHandlerPlus getInventory() {
        return inventory;
    }


    /**
     * Sets all inventory to null, called when wrenched, after inv is saved to nbt tags
     */
    public void emptyContent() {
        for (int i = 0; i < inventory.getSlots(); ++i) {
            this.inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    protected BasicMachine getMachineBlockType() {
        if (this.hasRotation) {
            return TGBlocks.BASIC_MACHINE;
        } else {
            return TGBlocks.SIMPLE_MACHINE;
        }
    }

    /**
     * Helper method to check if ItemStack output can be put into slot
     *
     * @param output
     * @param slot
     * @return
     */
    protected boolean canOutput(ItemStack output, int slot) {
        if (output.isEmpty()) {
            return true;
        }
        if (this.inventory.getStackInSlot(slot).isEmpty()) {
            return output.getCount() <= this.inventory.getSlotLimit(slot);
        } else {
            return this.inventory.getStackInSlot(slot).isItemEqual(output) && (this.inventory.getStackInSlot(slot).getCount() + output.getCount() <= this.inventory.getStackInSlot(slot).getMaxStackSize());
        }
    }

    /**
     * called when the block is broken
     */
    public void onBlockBreak() {
        InventoryUtil.dropInventoryItems(world, pos, inventory);
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(@NotNull NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(@NotNull NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        super.readFromNBT(compound);
    }

    public void writeNBTforDismantling(NBTTagCompound compound) {
        compound.setTag("inventory", inventory.serializeNBT());
        this.writeClientDataToNBT(compound);
    }

    public void readNBTfromStackTag(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        this.readClientDataFromNBT(compound);
    }

    /**
     * Called when a gui button is clicked, does nothing on default, override in subclasss
     *
     * @param id
     * @param ply
     * @param data
     */
    public void buttonClicked(int id, EntityPlayer ply, String data) {
    }


    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(@NotNull Capability<T> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory : super.getCapability(capability, facing);
    }

    @Override
    public boolean shouldRefresh(@NotNull World world, @NotNull BlockPos pos, @NotNull IBlockState oldState, @NotNull IBlockState newState) {
        if (!this.hasRotation) {
            return (oldState.getBlock() != newState.getBlock()) || (oldState.getValue(this.getMachineBlockType().MACHINE_TYPE) != newState.getValue(this.getMachineBlockType().MACHINE_TYPE));
        } else {
            return super.shouldRefresh(world, pos, oldState, newState);
        }
    }

    /**
     * @param contentsChanged the contentsChanged to set
     */
    public void setContentsChanged(boolean contentsChanged) {
        this.contentsChanged = contentsChanged;
    }

    /**
     * return true if a fluid interaction happened
     *
     * @param fluidhandleritem
     * @param stack
     * @return
     */
    public boolean onFluidContainerInteract(EntityPlayer player, EnumHand hand, IFluidHandlerItem fluidhandleritem, ItemStack stack) {
        return false;
    }
}
