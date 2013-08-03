package ds.mods.progsys;

import net.minecraft.block.material.Material;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import ds.mods.progsys.blocks.BlockController;
import ds.mods.progsys.blocks.BlockInventoryInterface;
import ds.mods.progsys.blocks.BlockWire;
import ds.mods.progsys.events.common.PlayerEvents;
import ds.mods.progsys.items.ItemWrench;
import ds.mods.progsys.net.PacDispat;
import ds.mods.progsys.net.PacketHandler;
import ds.mods.progsys.tile.TileEntityController;
import ds.mods.progsys.tile.TileEntityInventoryInterface;
import ds.mods.progsys.tile.TileEntityWire;


@Mod(modid="ProgSys",name="ProgSys")
@NetworkMod(channels={"ProgSys"}, packetHandler=PacketHandler.class, clientSideRequired = true, serverSideRequired = true)
public class ProgSys {
	
	public static BlockController controller;
	public static BlockWire wire;
	public static BlockInventoryInterface iinterface;
	
	public static ItemWrench wrench;
	
	@SidedProxy(serverSide = "ds.mods.progsys.CommonProxy",clientSide = "ds.mods.progsys.client.ClientProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
		FMLLog.info("Loading ProgSYS config...");
		Config.loadConfig(event.getSuggestedConfigurationFile());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		FMLLog.info("Loading ProgSYS...");
		controller = new BlockController(Config.ControllerID, Material.rock);
		controller.setUnlocalizedName("ds.progsys.controller");
		GameRegistry.registerBlock(controller, "Controller");
		LanguageRegistry.addName(controller, "ds.progsys.controller");
		GameRegistry.registerTileEntity(TileEntityController.class, "teController");
		
		wire = new BlockWire(Config.WireID, Material.rock);
		wire.setUnlocalizedName("ds.progsys.wire");
		GameRegistry.registerBlock(wire, "Wire");
		LanguageRegistry.addName(wire, "ds.progsys.wire");
		GameRegistry.registerTileEntity(TileEntityWire.class, "teWire");
		
		iinterface = new BlockInventoryInterface(Config.ItemInterfacerID, Material.rock);
		iinterface.setUnlocalizedName("ds.progsys.invinterface");
		GameRegistry.registerBlock(iinterface, "InventoryInterface");
		LanguageRegistry.addName(iinterface, "ds.progsys.invinterface");
		GameRegistry.registerTileEntity(TileEntityInventoryInterface.class, "teInvInterface");
		
		wrench = new ItemWrench(Config.WrenchID);
		wrench.setUnlocalizedName("ds.progsys.wrench");
		GameRegistry.registerItem(wrench, "Wrench");
		LanguageRegistry.addName(wrench, "ds.progsys.wrench");
		
		LanguageRegistry.instance().loadLocalization(getClass().getResource("/assets/progsys/lang/en_US.lang"), "en_US", false);
		
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
		
		proxy.registerRenderInfo();
		PacDispat.initPacketStuff();
	}
}
