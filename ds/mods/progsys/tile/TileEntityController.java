package ds.mods.progsys.tile;

import java.util.ArrayList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.mods.progsys.wirednet.Network;
import ds.mods.progsys.wirednet.NetworkDiscovery;
import ds.mods.progsys.wirednet.Vector3;
import ds.mods.progsys.wirednet.netbase.ControllerNetworkBase;

public class TileEntityController extends TileEntityNetworkBase implements IOnPlace {

	@Override
	public boolean canBeAddedToNetwork(Network net, ForgeDirection side) {
		if (net.controller == null)
		{
			System.out.println("adding");
			return true;
		}
		System.out.println("Conflict");
		this.conflictMap.put(side, "Network already contains a Controller!");
		return false;
	}

	@Override
	public void createNetworkBase(Network net) {
		this.netbase = new ControllerNetworkBase(new Vector3(xCoord,yCoord,zCoord));
	}

	@Override
	public void onPlace(EntityLivingBase entity, ItemStack stack) {
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
			if (netsame)
				firstNet.add(this,dirs.get(0));
			else
				NetworkDiscovery.startAdventure(this); //Come on vamanos! Everybody lets go!
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getAABBPool().getAABB(xCoord-1, yCoord, zCoord, xCoord + 2, yCoord + 5, zCoord + 1);
	}

	@Override
	public void createDefaultNetwork() {
		net = new Network(worldObj);
		net.add(this);
	}

}
