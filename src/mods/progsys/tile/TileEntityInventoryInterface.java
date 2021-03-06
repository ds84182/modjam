package ds.mods.progsys.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.mods.progsys.net.InventoryInfo;
import ds.mods.progsys.net.InventoryInterfaceState;
import ds.mods.progsys.net.PacDispat;
import ds.mods.progsys.wirednet.Network;
import ds.mods.progsys.wirednet.NetworkDiscovery;
import ds.mods.progsys.wirednet.Vector3;
import ds.mods.progsys.wirednet.drivers.ItemDriver;
import ds.mods.progsys.wirednet.netbase.DriverNetworkBase;

public class TileEntityInventoryInterface extends TileEntityNetworkBase implements IOnPlace, IOnRemove {
	public ForgeDirection facing = ForgeDirection.DOWN;
	public ItemDriver driver;
	public int tickDown = 20;
	public InventoryInfo invInfo;
	public boolean sendState = false;
	public int fiveSecTickDown = 5;
	public int fiveSecTickDowns = 0; //Now we can figure out the mode

	@Override
	public boolean canBeAddedToNetwork(Network net, ForgeDirection side) {
		return inventoryExists();
	}

	@Override
	public void createNetworkBase(Network net) {
		if (driver == null && netbase == null)
		{
			driver = new ItemDriver(null,this);
			netbase = new DriverNetworkBase(driver, new Vector3(xCoord,yCoord,zCoord));
		}
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
		tickDown--;
		if (this.showHolo && driver != null && driver.inv != null && !worldObj.isRemote)
		{
			if (tickDown == 0)
			{
				PacDispat.sendPacketToDimension(new InventoryInfo(driver.inv, new Vector3(xCoord,yCoord,zCoord)), worldObj.provider.dimensionId);
			}
			//Scan for any players within 32 blocks
			List l = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord-32, yCoord-32, zCoord-32, xCoord+32, yCoord+32, zCoord+32));
			if (l.isEmpty())
			{
				this.showHolo = false;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				PacDispat.sendPacketToDimension(new InventoryInterfaceState(this), worldObj.provider.dimensionId);
			}
		}
		if (tickDown == 0)
		{
			tickDown = 20;
		}
		if (sendState && !worldObj.isRemote)
		{
			sendState = false;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			PacDispat.sendPacketToDimension(new InventoryInterfaceState(this), worldObj.provider.dimensionId);
		}
		if (worldObj.isRemote)
		{
			tickDown--;
			if (tickDown == 0)
			{
				tickDown = 20;
				fiveSecTickDown--;
			}
			if (fiveSecTickDown == 0)
			{
				fiveSecTickDown = 5;
				fiveSecTickDowns++;
				PacDispat.sendPacketToDimension(new InventoryInterfaceState(this), worldObj.provider.dimensionId);
			}
		}
		if (ForgeDirection.VALID_DIRECTIONS[getBlockMetadata()] != facing)
		{
			facing = ForgeDirection.VALID_DIRECTIONS[getBlockMetadata()];
			//Dir changed!
			onRemove();
			onPlace();
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

	@Override
	public Packet getDescriptionPacket() {
		this.sendState = true;
		return null;
	}

	@Override
	public void onRemove() {
		if (net != null)
			net.remove(this);
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
		{
			if (arr.get(0).net != null)
				arr.get(0).net.add(this,dirs.get(0));
			else
				NetworkDiscovery.startAdventure(this);
		}
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		System.out.println("Loading NBT");
		showHolo = nbt.getBoolean("showHolo");
		if (driver == null || driver.filter == null || net == null)
		{
			createNetworkBase(net);
		}
		driver.filter.not = nbt.getBoolean("fltnot");
		NBTTagList list = nbt.getTagList("fltstacks");
		driver.filter.stacks.clear();
		for (int i = 0; i<list.tagCount(); ++i)
		{
			System.out.println(i);
			driver.filter.stacks.add(ItemStack.loadItemStackFromNBT((NBTTagCompound) list.tagAt(i)));
		}
		sendState = true;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("showHolo", showHolo);
		System.out.println("Saving "+nbt); 
		if (driver != null && driver.filter != null)
		{
			nbt.setBoolean("fltnot",driver.filter.not);
			NBTTagList list = new NBTTagList();
			for (ItemStack stack : driver.filter.stacks)
			{
				list.appendTag(stack.writeToNBT(new NBTTagCompound()));
			}
			nbt.setTag("fltstacks", list);
		}
	}

}
