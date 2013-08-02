package ds.mods.progsys.ai;

import java.util.ArrayList;

import ds.mods.progsys.tile.TileEntityController;
import ds.mods.progsys.wirednet.EnumNBType;
import ds.mods.progsys.wirednet.IDriver;
import ds.mods.progsys.wirednet.INetworkBase;
import ds.mods.progsys.wirednet.Network;

public class Controller {
	
	private TileEntityController tile;
	private Network net;
	private ArrayList<IDriver> driverList = new ArrayList<IDriver>();
	
	public Controller(TileEntityController t)
	{
		tile = t;
		net = tile.net;
	}
	
	public void runTick()
	{
		//Every tick we go though the network and we find 4 items that are out of place.
		//The item is counted as out of place if:
		/*
		 * The filter for the item interface has items, which one of those isn't that one
		 * If the item is in a place where the filter says that you cannot have any items
		 * If another item interface has that item and is a higher priority
		 */
		for (INetworkBase netbase : tile.net.tileMap.values())
		{
			if (netbase.getType() == EnumNBType.INTERFACE)
			{
				
			}
		}
	}
	
	
}
