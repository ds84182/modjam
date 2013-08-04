package ds.mods.progsys.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import ds.mods.progsys.CommonProxy;
import ds.mods.progsys.Config;
import ds.mods.progsys.client.render.CrystalItemRenderer;
import ds.mods.progsys.client.render.TileEntityControllerRenderer;
import ds.mods.progsys.client.render.TileEntityIInterfaceRenderer;
import ds.mods.progsys.client.render.TileEntityWireRenderer;
import ds.mods.progsys.tile.TileEntityController;
import ds.mods.progsys.tile.TileEntityInventoryInterface;
import ds.mods.progsys.tile.TileEntityWire;

public class ClientProxy extends CommonProxy {

	
	@Override
	public void registerRenderInfo()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityController.class, new TileEntityControllerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInventoryInterface.class, new TileEntityIInterfaceRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWire.class, new TileEntityWireRenderer());
		MinecraftForgeClient.registerItemRenderer(Config.CrystalID, new CrystalItemRenderer());
	}
	
	public World getClientWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}
}
