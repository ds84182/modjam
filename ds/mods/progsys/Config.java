package ds.mods.progsys;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class Config {
	
	public static int WireID;
	public static int ControllerID;
	public static int ItemInterfacerID;
	public static int FluidInterfacerID;
	public static int CrystalOreID;
	
	public static int WrenchID;
	
	public static void loadConfig(File file)
	{
		Configuration cfg = new Configuration(file);
		WireID = cfg.getBlock("Wire", 500, "The wires that connect things together and do those other things.").getInt();
		ControllerID = cfg.getBlock("Controller", 501, "The controller that controls those things, you know.").getInt();
		ItemInterfacerID = cfg.getBlock("ItemInterfacer", 502, "The item interfacer interfaces with item... has a good looking interface.").getInt();
		FluidInterfacerID = cfg.getBlock("FluidInterfacer", 503, "The fluid iterfacer lets you control your ingame fluid, not your IRL fluids. Just hold it in!").getInt();
		CrystalOreID = cfg.getBlock("CrystalOre", 504, "That beuatiful ore you see underground.").getInt();
		
		WrenchID = cfg.getItem("Wrench", 5000, "Use the wrench to configure stuff that can be wrenched.").getInt();
	}
}
