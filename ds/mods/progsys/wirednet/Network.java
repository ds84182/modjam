package ds.mods.progsys.wirednet;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import ds.mods.progsys.ai.Controller;
import ds.mods.progsys.tile.TileEntityController;
import ds.mods.progsys.tile.TileEntityNetworkBase;

public class Network {
	public HashMap<Vector3,INetworkBase> tileMap;
	public WeakReference<World> world;
	public INetworkBase controller;
	public Controller sys;
	
	public Network(World w)
	{
		tileMap = new HashMap<Vector3, INetworkBase>();
		world = new WeakReference<World>(w);
	}
	
	public void add(TileEntityNetworkBase nb) {
		add(nb,ForgeDirection.UNKNOWN);
	}

	public void add(TileEntityNetworkBase nb, ForgeDirection side) {
		if (!nb.canBeAddedToNetwork(this, side)) return;
		nb.createNetworkBase(this);
		INetworkBase netbase = nb.netbase;
		nb.net = this;
		if (nb instanceof TileEntityController)
		{
			controller = netbase;
			sys = ((TileEntityController)netbase).controller;
		}
		if (sys != null)
		{
			sys.dirtyDrivers = true;
		}
		tileMap.put(new Vector3(nb.xCoord,nb.yCoord,nb.zCoord), netbase);
	}
	
	
}
