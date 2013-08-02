package ds.mods.progsys.tile;

import java.util.EnumMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import ds.mods.progsys.wirednet.INetworkBase;
import ds.mods.progsys.wirednet.Network;

public abstract class TileEntityNetworkBase extends TileEntity {
	public Network net;
	public INetworkBase netbase;
	public EnumMap<ForgeDirection, String> conflictMap = new EnumMap<ForgeDirection, String>(ForgeDirection.class);
	public boolean showHolo = false;
	
	public abstract boolean canBeAddedToNetwork(Network net, ForgeDirection side);
	public abstract void createNetworkBase(Network net);
	public abstract void createDefaultNetwork();
	
	public void updateEntity()
	{
		if (net == null)
			createDefaultNetwork();
	}
}
