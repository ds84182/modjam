package ds.mods.progsys.tile;

import java.util.ArrayList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import ds.mods.progsys.wirednet.Network;
import ds.mods.progsys.wirednet.NetworkDiscovery;
import ds.mods.progsys.wirednet.Vector3;
import ds.mods.progsys.wirednet.netbase.WireNetworkBase;

public class TileEntityWire extends TileEntityNetworkBase implements IOnPlace, IOnRemove {

	@Override
	public boolean canBeAddedToNetwork(Network net, ForgeDirection side) {
		return true;
	}

	@Override
	public void createNetworkBase(Network net) {
		netbase = new WireNetworkBase(new Vector3(xCoord,yCoord,zCoord));
	}

	@Override
	public void onPlace() {
		//Check surroundings
		ArrayList<TileEntityNetworkBase> arr = new ArrayList<TileEntityNetworkBase>();
		ArrayList<ForgeDirection> dirs = new ArrayList<ForgeDirection>();
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
		{
			TileEntity tile = worldObj.getBlockTileEntity(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ);
			if (tile != null & tile instanceof TileEntityNetworkBase)
				arr.add((TileEntityNetworkBase) tile); dirs.add(dir);
		}
		
		if (arr.size() == 1)
			arr.get(0).net.add(this,dirs.get(0));
		else if (arr.size() > 1)
		{
			//We have a more complex situation.
			boolean netsame = true;
			Network firstNet = arr.get(0).net;
			for (TileEntityNetworkBase nb : arr)
			{
				if (nb.net != firstNet)
				{
					//Oh gawd!
					netsame = false;
					break;
				}
			}
			if (netsame && firstNet != null)
				firstNet.add(this,dirs.get(0));
			else
				NetworkDiscovery.startAdventure(this); //Come on vamanos! Everybody lets go!
		}
	}

	@Override
	public void createDefaultNetwork() {
		net = new Network(worldObj);
	}

	@Override
	public void onRemove() {
		net.remove(this); //TODO: Make Dora explore on all sides.
	}

}
