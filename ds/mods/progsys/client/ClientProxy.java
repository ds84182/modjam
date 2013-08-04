package ds.mods.progsys.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import ds.mods.progsys.CommonProxy;
import ds.mods.progsys.client.render.OreRenderingHandler;
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
		this.CrystalRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(CrystalRenderID, new OreRenderingHandler());
	}
	
	public World getClientWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}
}
