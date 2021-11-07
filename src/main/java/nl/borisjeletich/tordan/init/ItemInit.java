package nl.borisjeletich.tordan.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import nl.borisjeletich.tordan.objects.items.ItemBase;

public class ItemInit {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static final Item LYN_RING = new ItemBase("lyn_ring");
	public static final Item LYN_BRACELET = new ItemBase("lyn_bracelet");
	
	
	public static final Item COPPER_INGOT = new ItemBase("copper_ingot");
	public static final Item SILVER_INGOT = new ItemBase("silver_ingot");
}
