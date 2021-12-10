package nl.borisjeletich.tordan.objects.blocks.tileentity;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import nl.borisjeletich.tordan.energy.CustomEnergyStorage;

import java.util.Random;

public class TileEntityCopperRod extends TileEntity implements ITickable {

    private CustomEnergyStorage storage = new CustomEnergyStorage(100);
    private String customName;
    public int energy = storage.getEnergyStored();

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return (T)this.storage;
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) return true;
        return super.hasCapability(capability, facing);

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("energy", this.energy);
        this.storage.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.energy = compound.getInteger("energy");
        this.storage.readFromNBT(compound);
    }

    public int getEnergyStored() {
        return this.energy;
    }

    public int getMaxEnergyStored() {
        return this.storage.getMaxEnergyStored();
    }
    @Override
    public void update() {
        if (world.isThundering() == true) {
            int min = 1;
            int max = 100;
            int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
            if (random_int == 50) {
                world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false));
                this.energy += 10;
            }
        }
   }
}
