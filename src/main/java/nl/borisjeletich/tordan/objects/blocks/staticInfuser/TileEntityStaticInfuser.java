package nl.borisjeletich.tordan.objects.blocks.staticInfuser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
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
        boolean flag = this.isActive();
        boolean flag1 = false;
        if (this.isActive()) --this.infuserWorkTime; {
            if (!this.world.isRemote) {
                ItemStack itemstack = (ItemStack)this.infuserItemStacks.get(0);
                if (this.infuserWorkTime == 0 && this.canInfuse(itemstack)) {
                    this.totalWorkTime = this.getInfuserWorkTime();
                    this.infuserWorkTime = this.totalWorkTime;
                    flag1 = true;

                    if(!itemstack.isEmpty()) {
                        Item item = itemstack.getItem();
                        itemstack.shrink(1);

                        if(itemstack.isEmpty()) {
                            ItemStack itemstack1 = ((Item) item).getContainerItem(itemstack);
                            this.infuserItemStacks.set(0, itemstack1);
                        }
                    }
                }
            }
            if(this.isActive()){
                ++this.infuserWorkTime;

                if(this.infuserWorkTime >= this.totalWorkTime) {
                    this.infuserWorkTime = 0;
                    this.totalWorkTime = this.getInfuserWorkTime((ItemStack)this.infuserItemStacks.get(0), (ItemStack)this.infuserItemStacks.get(1));
                    this.infuseItem();
                    flag1 = true;
                }
            }
            else this.infuserWorkTime = 0;
        }
        else if (!this.isActive() && this.infuserWorkTime > 0) {
            this.infuserWorkTime = MathHelper.clamp(this.infuserWorkTime - 2, 0, this.totalWorkTime);
        }
        if (flag != this.isActive()) {
            flag1 = true;
            StaticInfuser.setState(this.isActive(), this.world, this.pos);
        }
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

    public void infuseItem(){
        if(canInfuserWork()){
            ItemStack input = (ItemStack)this.infuserItemStacks.get(0);
            ItemStack result = InfuserRecipes.getInstance().getInfuserResult((ItemStack)this.infuserItemStacks.get(0));
            ItemStack output = (ItemStack)this.infuserItemStacks.get(1);

            if(output.isEmpty()) this.infuserItemStacks.set(1, result.copy());
            else if(output.getItem() == result.getItem()) output.grow(result.getCount());
            input.shrink(1);
        }
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }



    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if(((ItemStack)this.infuserItemStacks.get(1)).isEmpty()) return true;
        else return false;
    }

    @Override
    public int getField(int id) {
        switch(id) {
            case 0:
                return this.infuserWorkTime;
            case 1:
                return this.totalWorkTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch(id) {
            case 0:
                this.infuserWorkTime = value;
                break;
            case 1:
                this.totalWorkTime = value;
                break;
        }
    }
}
