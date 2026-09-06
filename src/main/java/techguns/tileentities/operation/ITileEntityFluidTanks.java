package techguns.tileentities.operation;

import net.minecraft.nbt.NBTTagCompound;

public interface ITileEntityFluidTanks {
    void saveTanksToNBT(NBTTagCompound tags);

    void loadTanksFromNBT(NBTTagCompound tags);
}
