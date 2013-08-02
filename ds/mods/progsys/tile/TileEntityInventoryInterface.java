package ds.mods.progsys.tile;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import ds.mods.progsys.wirednet.Network;
import ds.mods.progsys.wirednet.Vector3;
import ds.mods.progsys.wirednet.drivers.ItemDriver;
import ds.mods.progsys.wirednet.netbase.DriverNetworkBase;

public class TileEntityInventoryInterface extends TileEntityNetworkBase {
	public ForgeDirection facing = ForgeDirection.DOWN;
	public ItemDriver driver;

	@Override
	public boolean canBeAddedToNetwork(Network net, ForgeDirection side) {
		return inventoryExists();
	}

	@Override
	public void createNetworkBase(Network net) {
		driver = new ItemDriver(null);
		netbase = new DriverNetworkBase(driver, new Vector3(xCoord,yCoord,zCoord));
	}

	@Override
	public void createDefaultNetwork() {
		net = new Network(worldObj);
		net.add(this);
	}
	
	public boolean inventoryExists()
	{
		Vector3 invv = new Vector3(xCoord,yCoord,zCoord).add(facing);
		TileEntity tile = worldObj.getBlockTileEntity(invv.x, invv.y, invv.z);
		return tile != null && tile instanceof IInventory;
	}
	
	public IInventory getInventory()
	{
		if (!inventoryExists()) return null;
		Vector3 invv = new Vector3(xCoord,yCoord,zCoord).add(facing);
		TileEntity tile = worldObj.getBlockTileEntity(invv.x, invv.y, invv.z);
		return (IInventory) tile;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (ForgeDirection.VALID_DIRECTIONS[getBlockMetadata()] != facing)
		{
			facing = ForgeDirection.VALID_DIRECTIONS[getBlockMetadata()];
			//Dir changed!
			if (inventoryExists())
			{
				if (netbase != null)
				{
					driver.inv = getInventory();
				}
			}
		}
		else
		{
			if (driver != null)
				if (driver.inv != getInventory())
				{
					driver.inv = getInventory();
				}
		}
	}

}
