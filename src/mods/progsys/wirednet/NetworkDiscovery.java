package ds.mods.progsys.wirednet;

import java.util.ArrayList;
import java.util.Stack;

import net.minecraftforge.common.ForgeDirection;
import ds.mods.progsys.tile.TileEntityNetworkBase;

public class NetworkDiscovery {
	public static Network startAdventure(TileEntityNetworkBase netbase)
	{
		Network net = new Network(netbase.worldObj);
		
		Stack<Vector3> stack = new Stack<Vector3>();
		Stack<ForgeDirection> fdirStack = new Stack<ForgeDirection>();
		stack.push(new Vector3(netbase.xCoord,netbase.yCoord,netbase.zCoord));
		fdirStack.push(ForgeDirection.UNKNOWN);
		
		ArrayList<Vector3> alreadyDone = new ArrayList<Vector3>();
		
		while (!stack.isEmpty())
		{
			Vector3 vec = stack.pop();
			System.out.println("found "+vec);
			ForgeDirection dir = fdirStack.pop();
			if (netbase.worldObj.getBlockTileEntity(vec.x, vec.y, vec.z) != null & netbase.worldObj.getBlockTileEntity(vec.x, vec.y, vec.z) instanceof TileEntityNetworkBase)
			{
				TileEntityNetworkBase nb = (TileEntityNetworkBase) netbase.worldObj.getBlockTileEntity(vec.x, vec.y, vec.z);
				if (nb.canBeAddedToNetwork(net, dir))
				{
					net.add(nb,dir.getOpposite());
					alreadyDone.add(vec);
					
					for (ForgeDirection fdir : ForgeDirection.VALID_DIRECTIONS)
					{
						if (fdir != dir)
						{
							if (!alreadyDone.contains(vec.add(fdir)))
							{
								stack.push(vec.add(fdir));
								fdirStack.push(fdir.getOpposite());
							}
						}
					}
				}
			}
		}
		
		return net;
	}
}
