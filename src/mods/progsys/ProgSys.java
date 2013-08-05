package ds.mods.progsys;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.mods.progsys.blocks.BlockController;
import ds.mods.progsys.blocks.BlockCrystalOre;
import ds.mods.progsys.blocks.BlockInventoryInterface;
import ds.mods.progsys.blocks.BlockWire;
import ds.mods.progsys.events.common.PlayerEvents;
import ds.mods.progsys.items.ItemCrystal;
import ds.mods.progsys.items.ItemHolos;
import ds.mods.progsys.items.ItemWrench;
import ds.mods.progsys.net.PacDispat;
import ds.mods.progsys.net.PacketHandler;
import ds.mods.progsys.oregen.CrystalGenerator;
import ds.mods.progsys.tile.TileEntityController;
import ds.mods.progsys.tile.TileEntityInventoryInterface;
import ds.mods.progsys.tile.TileEntityWire;


@Mod(modid="ProgSys",name="ProgSys")
@NetworkMod(channels={"ProgSys"}, packetHandler=PacketHandler.class, clientSideRequired = true, serverSideRequired = true)
public class ProgSys {
	//TODO: Reprogram the whole mod in Lua for better extendabillity
	
	public static BlockController controller;
	public static BlockWire wire;
	public static BlockInventoryInterface iinterface;
	public static BlockCrystalOre crystalOre;
	
	public static ItemWrench wrench;
	public static ItemCrystal crystal;
	public static ItemHolos holos;
	
	@SideOnly(Side.CLIENT)
	public static CreativeTabs tab = new CreativeTabs("ProgSys");
	
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
		
		crystalOre = new BlockCrystalOre(Config.CrystalOreID, Material.rock);
		crystalOre.setUnlocalizedName("ds.progsys.crystalore");
		GameRegistry.registerBlock(crystalOre, "CrystalOre");
		LanguageRegistry.addName(crystalOre, "ds.progsys.crystalOre");
		
		wrench = new ItemWrench(Config.WrenchID);
		wrench.setUnlocalizedName("ds.progsys.wrench");
		GameRegistry.registerItem(wrench, "Wrench");
		LanguageRegistry.addName(wrench, "ds.progsys.wrench");
		
		crystal = new ItemCrystal(Config.CrystalID);
		crystal.setUnlocalizedName("ds.progsys.crystal");
		GameRegistry.registerItem(crystal, "Crystal");
		LanguageRegistry.addName(crystal, "ds.progsys.crystal");
		
		holos = new ItemHolos(Config.HolosID);
		holos.setUnlocalizedName("ds.progsys.holos");
		GameRegistry.registerItem(holos, "Holos");
		LanguageRegistry.instance().addStringLocalization("item.ds.progsys.holos.pixel.name", "ds.progsys.holopixel");
		LanguageRegistry.instance().addStringLocalization("item.ds.progsys.holos.array.name", "ds.progsys.holoarray");
		
		LanguageRegistry.instance().loadLocalization(getClass().getResource("/assets/progsys/lang/en_US.lang"), "en_US", false);
		
		MinecraftForge.EVENT_BUS.register(new PlayerEvents());
		
		GameRegistry.registerWorldGenerator(new CrystalGenerator());
		
		//CRAFTING!
		GameRegistry.addShapelessRecipe(new ItemStack(holos), new Object[]{
			new ItemStack(crystal),new ItemStack(crystal),new ItemStack(crystal),
			new ItemStack(crystal),new ItemStack(crystal),new ItemStack(crystal),
			new ItemStack(crystal),new ItemStack(crystal),new ItemStack(crystal)
		});
		
		GameRegistry.addShapelessRecipe(new ItemStack(holos,2,1), new Object[]{
			new ItemStack(holos),new ItemStack(holos),new ItemStack(holos),
			new ItemStack(holos),new ItemStack(holos),new ItemStack(holos),
			new ItemStack(holos),new ItemStack(holos),new ItemStack(holos)
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(controller), new Object[]{
			"HHH",
			"PRP",
			"RPR",
			'H',new ItemStack(holos,2,1),
			'P',new ItemStack(Block.pistonBase),
			'R',new ItemStack(Block.blockRedstone)
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(iinterface), new Object[]{
			"HHH",
			"CRC",
			"RPR",
			'H',new ItemStack(holos,2,1),
			'P',new ItemStack(Block.pistonBase),
			'R',new ItemStack(Block.blockRedstone),
			'C',new ItemStack(Block.chest)
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(wire,4), new Object[]{
			"RRR",
			"SSS",
			"RRR",
			'R',new ItemStack(Item.redstone),
			'S',new ItemStack(Item.silk),
		});
		
		GameRegistry.addShapedRecipe(new ItemStack(wrench), new Object[]{
			"I I",
			"III",
			" W ",
			'I',new ItemStack(Item.ingotIron),
			'W',new ItemStack(Block.cloth,1,14),
		});
		
		proxy.registerRenderInfo();
		PacDispat.initPacketStuff();
	}
}
