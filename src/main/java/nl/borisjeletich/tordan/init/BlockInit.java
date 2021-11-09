package nl.borisjeletich.tordan.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import nl.borisjeletich.tordan.Tordan;
import nl.borisjeletich.tordan.objects.blocks.BlockBase;
import nl.borisjeletich.tordan.objects.blocks.CopperRod;
import nl.borisjeletich.tordan.objects.blocks.staticInfuser.StaticInfuser;

public class BlockInit {
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block LYN_BATTERY = new BlockBase("lyn_battery", Material.IRON);
	public static final Block COPPER_ORE = new BlockBase("copper_ore", Material.ROCK);
	public static final Block SILVER_ORE = new BlockBase("silver_ore", Material.ROCK);
	public static final Block COPPER_ROD = new CopperRod("copper_rod", Material.IRON);
	public static final Block STATIC_INFUSER_OFF = new StaticInfuser("static_infuser", false).setCreativeTab(Tordan.TORDANMODTAB);
	public static final Block STATIC_INFUSER_ON = new StaticInfuser("static_infuser_on", true);
}
