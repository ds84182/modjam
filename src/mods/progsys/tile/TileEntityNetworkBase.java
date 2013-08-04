package ds.mods.progsys.tile;

import java.util.EnumMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.mods.progsys.client.holo.HoloGui;
import ds.mods.progsys.wirednet.INetworkBase;
import ds.mods.progsys.wirednet.Network;

public abstract class TileEntityNetworkBase extends TileEntity {
	public Network net;
	public INetworkBase netbase;
	public EnumMap<ForgeDirection, String> conflictMap = new EnumMap<ForgeDirection, String>(ForgeDirection.class);
	public boolean showHolo = false;
	public boolean needsPlace = false;
	public int tickNum = 0;
	@SideOnly(Side.CLIENT)
	public HoloGui gui;
	
	public abstract boolean canBeAddedToNetwork(Network net, ForgeDirection side);
	public abstract void createNetworkBase(Network net);
	public abstract void createDefaultNetwork();
	
	public void updateEntity()
	{
		if (net == null)
			createDefaultNetwork();
		tickNum++;
		if (tickNum > 1) //First tick is dedicated to setting up networks
			if (needsPlace)
			{
				((IOnPlace)this).onPlace();
				needsPlace = !needsPlace;
			}
	}
	@Override
	public void invalidate() {
		if (this instanceof IOnRemove)
		{
			((IOnRemove)this).onRemove();
		}
		super.invalidate();
	}
	
	@Override
	public void validate() {
		if (this instanceof IOnPlace)
		{
			needsPlace = true;
		}
		super.validate();
	}
	
}
