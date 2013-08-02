package ds.mods.progsys.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import ds.mods.progsys.CommonProxy;
import ds.mods.progsys.client.render.TileEntityControllerRenderer;
import ds.mods.progsys.tile.TileEntityController;

public class ClientProxy extends CommonProxy {

	
	@Override
	public void registerRenderInfo()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityController.class, new TileEntityControllerRenderer());
	}
}
