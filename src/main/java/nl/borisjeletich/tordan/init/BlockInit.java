package nl.borisjeletich.tordan.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import nl.borisjeletich.tordan.objects.blocks.BlockBase;
import nl.borisjeletich.tordan.objects.blocks.CopperRod;

public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block LYN_BATTERY = new BlockBase("lyn_battery", Material.IRON);
	public static final Block COPPER_ORE = new BlockBase("copper_ore", Material.IRON);
	public static final Block SILVER_ORE = new BlockBase("silver_ore", Material.IRON);
	public static final Block COPPER_ROD = new CopperRod("copper_rod", Material.IRON);
}
