package ds.mods.progsys.wirednet;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import net.minecraft.world.World;

public class Network {
	public HashMap<Vector3,INetworkBase> tileMap;
	public WeakReference<World> world;
	
	public Network(World w)
	{
		tileMap = new HashMap<Vector3, INetworkBase>();
		world = new WeakReference<World>(w);
	}
	
	
}
