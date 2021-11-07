package nl.borisjeletich.tordan.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import nl.borisjeletich.tordan.Tordan;
import nl.borisjeletich.tordan.init.BlockInit;
import nl.borisjeletich.tordan.init.ItemInit;
import nl.borisjeletich.tordan.util.interfaces.IHasModel;

public class BlockBase extends Block implements IHasModel {

	public BlockBase(String name, Material material) {
		
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Tordan.TORDANMODTAB);
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void registerModels() {
		Tordan.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
