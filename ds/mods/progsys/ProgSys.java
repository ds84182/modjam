package ds.mods.progsys;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;


@Mod(modid="ProgSys",name="ProgSys")
@NetworkMod()
public class ProgSys {
	
	
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
		Config.loadConfig(event.getSuggestedConfigurationFile());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		
	}
}
