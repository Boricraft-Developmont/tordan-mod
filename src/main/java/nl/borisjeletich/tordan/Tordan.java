package nl.borisjeletich.tordan;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import nl.borisjeletich.tordan.proxy.CommonProxy;
import nl.borisjeletich.tordan.recipes.SmeltingRecipes;
import nl.borisjeletich.tordan.tabs.TordanTab;
import nl.borisjeletich.tordan.util.Reference;

@Mod(modid = Reference.MOD_ID, version = Reference.MC_VERSION, name = Reference.NAME)
public class Tordan {
	
	@Instance
	public static Tordan instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static final CreativeTabs TORDANMODTAB = new TordanTab("tabback");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		SmeltingRecipes.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
	
	@EventHandler
	public void serverInit(FMLServerStartingEvent event) {
		
	}

}
