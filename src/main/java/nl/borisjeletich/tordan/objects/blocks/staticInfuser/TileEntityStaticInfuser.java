package nl.borisjeletich.tordan.objects.blocks.staticInfuser;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityStaticInfuser extends TileEntity implements ITickable, ISidedInventory {

    private static final int[] slotsTop = new int[] {0};
    private static final int[] slotsBottom = new int[] {2, 1};
    private static final int[] slotsSides = new int[] {1};
    private NonNullList<ItemStack> infuserItemStacks = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);

    private int infuserWorkTime;
    private int totalWorkTime;

    public int getSizeInventory() {
        return this.infuserItemStacks.size();
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.infuserItemStacks) {
            if (!itemstack.isEmpty()) return false;
        }
        return true;
    }

    public ItemStack getStackInSlot(int index) {
        return (ItemStack)this.infuserItemStacks.get(index);
    }

    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.infuserItemStacks, index, count);
    }

    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.infuserItemStacks, index);
    }

    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = (ItemStack)this.infuserItemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.infuserItemStacks.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
        if (index == 0 && !flag) {
            this.markDirty();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.infuserItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.infuserItemStacks);
        this.infuserWorkTime = compound.getInteger("InfuserWorkTime");
        this.totalWorkTime = compound.getInteger("TotalWorkTime");

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
        compound.setInteger("InfuserWorkTime", this.infuserWorkTime);
        compound.setInteger("TotalWorkTime", this.totalWorkTime);
        ItemStackHelper.saveAllItems(compound, this.infuserItemStacks);

        return compound;
    }

    public int getInventoryStackLimit() {
        return 1;
    }

    public boolean isActive() {
        return this.infuserWorkTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isActive(IInventory infuser) {
        return infuser.getField(0) > 0;
    }

    public void update() {
    }

    public int getInfuserWorkTime() {
        return 200;
    }

    private boolean canInfuserWork() {
        if(((ItemStack)this.infuserItemStacks.get(0)).isEmpty()) return false;
        else {
            ItemStack result = InfuserRecipes.getInstance().getInfuserResult((ItemStack)this.infuserItemStacks.get(0));
            if(result.isEmpty()) return false;
            else {
                ItemStack output = (ItemStack)this.infuserItemStacks.get(0);
                if(output.isEmpty()) return false;
                if(!output.isItemEqual(result)) return false;
                int res = output.getCount() + result.getCount();
                return res <= getInventoryStackLimit() && res <= output.getMaxStackSize();

            }
        }
    }
// TODO Add Infuser recipe checking
    public void infuseItem(){

    }
}
