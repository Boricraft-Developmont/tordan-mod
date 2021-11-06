package nl.borisjeletich.tordan.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import nl.borisjeletich.tordan.init.ItemInit;

public class TordanTab extends CreativeTabs{
	
	public TordanTab(String label) {
		
		super("tordanmodtab");
		this.setBackgroundImageName("tabback.png");
	}
	
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ItemInit.LYN_RING);
	}
}
