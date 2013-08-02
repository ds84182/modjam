package ds.mods.progsys.tile;

import net.minecraftforge.common.ForgeDirection;
import ds.mods.progsys.wirednet.Network;

public class TileEntityInventoryInterface extends TileEntityNetworkBase {

	@Override
	public boolean canBeAddedToNetwork(Network net, ForgeDirection side) {
		return true;
	}

	@Override
	public void createNetworkBase(Network net) {
		
	}

	@Override
	public void createDefaultNetwork() {
		net = new Network(worldObj);
	}

}
