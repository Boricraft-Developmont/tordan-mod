package nl.borisjeletich.tordan.objects.blocks.staticInfuser;

import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class InfuserRecipes {
    private static final InfuserRecipes INSTANCE = new InfuserRecipes();
    //todo: maybe add argument back in
    private final Map<ItemStack, ItemStack> infusingList = Maps.newHashMap();
    private final Map<ItemStack, Float> experienceList = Maps.newHashMap();

    public static InfuserRecipes getInstance() {
        return INSTANCE;
    }
    private InfuserRecipes() {
        addInfusing(new ItemStack(), new ItemStack(Item.getItemFromBlock(), 1, 1), 1.0F);
    }
    public void addInfusing(ItemStack input, ItemStack output, float experience) {
        if(getInfusingResult(input) != ItemStack.EMPTY) return;
        this.infusingList.put(input, output);
        this.experienceList.put(output, experience);
    }

    public ItemStack getInfusingResult(ItemStack input) {
        for(Map.Entry<ItemStack, ItemStack> entry : this.infusingList.entrySet()) {
            if(this.compareItemStacks(input, entry.getKey())) {
                return entry.getValue();
            }
        }
        return ItemStack.EMPTY;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public Map<ItemStack, ItemStack> getInfusingList() {
        return this.infusingList;
    }

    public float getInfusingExperience(ItemStack stack) {
        float ret = stack.getItem().getSmeltingExperience(stack);
        if (ret != -1) return ret;

        for (Map.Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
            if (this.compareItemStacks(stack, entry.getKey())) {
                return entry.getValue();
            }
        }
        return 0.0F;
    }
}
