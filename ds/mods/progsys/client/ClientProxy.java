package ds.mods.progsys.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.ClientRegistry;
import ds.mods.progsys.CommonProxy;
import ds.mods.progsys.client.render.TileEntityControllerRenderer;
import ds.mods.progsys.client.render.TileEntityIInterfaceRenderer;
import ds.mods.progsys.tile.TileEntityController;
import ds.mods.progsys.tile.TileEntityInventoryInterface;

public class ClientProxy extends CommonProxy {

	
	@Override
	public void registerRenderInfo()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityController.class, new TileEntityControllerRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInventoryInterface.class, new TileEntityIInterfaceRenderer());
	}
	
	public World getClientWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}
}
