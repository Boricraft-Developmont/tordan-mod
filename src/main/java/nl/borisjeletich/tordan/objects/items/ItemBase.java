package nl.borisjeletich.tordan.objects.items;

import net.minecraft.item.Item;
import nl.borisjeletich.tordan.Tordan;
import nl.borisjeletich.tordan.init.ItemInit;
import nl.borisjeletich.tordan.util.interfaces.IHasModel;

public class ItemBase extends Item implements IHasModel {
	
	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Tordan.TORDANMODTAB);
		
		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		
		Tordan.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
