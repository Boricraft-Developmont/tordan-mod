package nl.borisjeletich.tordan.objects.blocks.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import nl.borisjeletich.tordan.energy.CustomEnergyStorage;

public class TileEntityEnergy extends TileEntity implements ITickable {

    private CustomEnergyStorage storage = new CustomEnergyStorage(10);

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY) return (T)this.storage;
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return true;
        return super.hasCapability(capability, facing);

    }

    @Override
    public void update() {
        this.storage.receiveEnergy(1, false);
        this.storage.extractEnergy(1, false);
    }

}
