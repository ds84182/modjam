package ds.mods.progsys.tile;

import net.minecraftforge.common.ForgeDirection;
import ds.mods.progsys.wirednet.Network;
import ds.mods.progsys.wirednet.Vector3;
import ds.mods.progsys.wirednet.drivers.ItemDriver;
import ds.mods.progsys.wirednet.netbase.DriverNetworkBase;

public class TileEntityInventoryInterface extends TileEntityNetworkBase {

	@Override
	public boolean canBeAddedToNetwork(Network net, ForgeDirection side) {
		return true;
	}

	@Override
	public void createNetworkBase(Network net) {
		netbase = new DriverNetworkBase(new ItemDriver(null), new Vector3(xCoord,yCoord,zCoord));
	}

	@Override
	public void createDefaultNetwork() {
		net = new Network(worldObj);
	}

}
