package ds.mods.progsys.net;

import ds.mods.progsys.tile.TileEntityInventoryInterface;
import ds.mods.progsys.wirednet.ItemFilter;
import ds.mods.progsys.wirednet.Vector3;

public class InventoryInterfaceState {
	public boolean showHolo;
	public ItemFilter filter;
	public Vector3 pos;
	
	public InventoryInterfaceState()
	{
		
	}
	
	public InventoryInterfaceState(TileEntityInventoryInterface tile)
	{
		showHolo = tile.showHolo;
		pos = new Vector3(tile.xCoord, tile.yCoord, tile.zCoord);
		if (tile.driver != null)
			filter = tile.driver.filter;
	}
}
