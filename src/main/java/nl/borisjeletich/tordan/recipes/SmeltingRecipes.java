package nl.borisjeletich.tordan.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nl.borisjeletich.tordan.init.BlockInit;
import nl.borisjeletich.tordan.init.ItemInit;

public class SmeltingRecipes {
	
	public static void init() {
		
		GameRegistry.addSmelting(new ItemStack(BlockInit.COPPER_ORE), new ItemStack(ItemInit.COPPER_INGOT), 0);
		GameRegistry.addSmelting(new ItemStack(BlockInit.SILVER_ORE), new ItemStack(ItemInit.SILVER_INGOT), 0);
	}
}
