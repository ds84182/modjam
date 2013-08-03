package ds.mods.progsys.ai;

import java.util.ArrayList;
import java.util.Stack;

import net.minecraft.item.ItemStack;
import ds.mods.progsys.tile.TileEntityController;
import ds.mods.progsys.wirednet.EnumDriverType;
import ds.mods.progsys.wirednet.EnumNBType;
import ds.mods.progsys.wirednet.IDriver;
import ds.mods.progsys.wirednet.INetworkBase;
import ds.mods.progsys.wirednet.ItemFilter;
import ds.mods.progsys.wirednet.Network;

public class Controller {
	
	private TileEntityController tile;
	private Network net;
	private ArrayList<IDriver> driverList = new ArrayList<IDriver>();
	public boolean dirtyDrivers = true;
	public Stack<StackInfo> moveQueue = new Stack<Controller.StackInfo>();
	
	public Controller(TileEntityController t)
	{
		tile = t;
		net = tile.net;
	}
	
	public void runTick()
	{
		//Every tick we go though the network and we find 4 items that are out of place.
		//The item is counted as out of place if:
		/*
		 * The filter for the item interface has items, which one of those isn't that one
		 * If the item is in a place where the filter says that you cannot have any items
		 * If another item interface has that item and is a higher priority
		 */
		if (tile.net != net)
		{
			dirtyDrivers = true;
			net = tile.net;
		}
		if (dirtyDrivers)
		{
			System.out.println("Drivers dirty");
			driverList.clear();
			moveQueue.clear();
			for (INetworkBase netbase : tile.net.tileMap.values())
			{
				if (netbase.getType() == EnumNBType.INTERFACE)
				{
					System.out.println("Found "+netbase);
					driverList.add(netbase.getInterfaceDriver());
				}
			}
			dirtyDrivers = false;
		}
		
		for (IDriver driver : driverList)
		{
			//Finding out of place items in item drivers
			if (driver != null)
			{
				if (driver.getType() == EnumDriverType.ITEM)
				{
					//System.out.println("Itemdriver");
					ItemFilter filter = driver.getItemFilter();
					if (filter != null)
					{
						for (int i = 0; i<driver.getSize(); i++)
						{
							ItemStack stack = driver.getStack(i);
							if (stack != null)
							{
								if (!filter.matchesFilter(stack) && !moveQueue.contains(new StackInfo(driver, i)))
								{
									//Add it onto the move queue
									moveQueue.push(new StackInfo(driver, i));
									//System.out.println("Need to move "+stack.getItem().getUnlocalizedName());
								}
								else if (filter.matchesFilter(stack) && tile.worldObj != null)
								{
									//System.out.println(stack.getItem().getUnlocalizedName()+" ok!");
								}
							}
						}
					}
				}
			}
			else
			{
				//System.out.println("NULLDRIVER");
			}
		}
		
		//Process 4 items on the move queue
		for (int i = 0; i<4; i++)
		{
			if (!moveQueue.isEmpty())
			{
				//Find a place for the new item
				/*
				 * Priority:
				 * First check for any filter with this item
				 * Then check for any filter that is in not mode without this item
				 * Then check for any filter that is not in not mode with no items
				 */
				StackInfo info = moveQueue.pop();
				for (IDriver driver : driverList)
				{
					if (driver != null)
					{
						//For right now, we will only check for things without anything in the filter
						ItemFilter filter = driver.getItemFilter();
						if (filter != null)
						{
							if (!filter.not && filter.stacks.size() == 0)
							{
								//Move the item here
								System.out.println("Found place");
								driver.setStack(0,info.driver.getStackAndRemove(info.slot)); //TODO: Look to see if it has space
							}
						}
					}
				}
			}
			else
			{
				break;
			}
		}
	}
	
	private class StackInfo
	{
		public IDriver driver;
		public int slot;
		public StackInfo(IDriver d, int s)
		{
			driver = d;
			slot = s;
		}
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof StackInfo)
			{
				StackInfo other = ((StackInfo)obj);
				return other.driver == driver & other.slot == slot;
			}
			return false;
		}
	}
}
