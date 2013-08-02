package ds.mods.progsys;

import net.minecraft.block.material.Material;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import ds.mods.progsys.blocks.BlockController;
import ds.mods.progsys.blocks.BlockWire;
import ds.mods.progsys.tile.TileEntityController;
import ds.mods.progsys.tile.TileEntityWire;


@Mod(modid="ProgSys",name="ProgSys")
@NetworkMod()
public class ProgSys {
	
	public static BlockController controller;
	public static BlockWire wire;
	
	
	@SidedProxy(serverSide = "ds.mods.progsys.CommonProxy",clientSide = "ds.mods.progsys.client.ClientProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
		Config.loadConfig(event.getSuggestedConfigurationFile());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		controller = new BlockController(Config.ControllerID, Material.rock);
		GameRegistry.registerBlock(controller, "Controller");
		LanguageRegistry.addName(controller, "ds.progsys.controller");
		GameRegistry.registerTileEntity(TileEntityController.class, "teController");
		
		wire = new BlockWire(Config.WireID, Material.rock);
		GameRegistry.registerBlock(wire, "Wire");
		LanguageRegistry.addName(wire, "ds.progsys.wire");
		GameRegistry.registerTileEntity(TileEntityWire.class, "teWire");
		
		proxy.registerRenderInfo();
	}
}
