package ds.mods.progsys.ai;

import java.util.ArrayList;
import java.util.HashMap;
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
	public ArrayList<IDriver> driverList = new ArrayList<IDriver>();
	public boolean dirtyDrivers = true;
	public Stack<StackInfo> moveQueue = new Stack<Controller.StackInfo>();
	public ArrayList<StackInfo> locks = new ArrayList<Controller.StackInfo>();
	public HashMap<IDriver,ArrayList<ArrayList<IDriver>>> multiples = new HashMap<IDriver, ArrayList<ArrayList<IDriver>>>(); //Stores the multiples of driver stuff.
	public int cooldown = 0; //Cooldown = stacksize/8
	
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
			multiples.clear();
			for (INetworkBase netbase : tile.net.tileMap.values())
			{
				if (netbase.getType() == EnumNBType.INTERFACE)
				{
					System.out.println("Found "+netbase);
					driverList.add(netbase.getInterfaceDriver());
					
				}
			}
			for (IDriver driver : driverList)
			{
				System.out.println("Finding dupe filter params");
				ArrayList<ArrayList<IDriver>> whereIsYourGodNow = new ArrayList<ArrayList<IDriver>>();
				for (ItemStack stack : driver.getItemFilter().stacks)
				{
					ArrayList<IDriver> dupes = new ArrayList<IDriver>();
					whereIsYourGodNow.add(dupes);
					for (IDriver drv : driverList)
					{
						if (drv != driver)
						{
							if (driver.getItemFilter().not == drv.getItemFilter().not)
								for (ItemStack stk : drv.getItemFilter().stacks)
								{
									if (stk.isItemEqual(stack))
									{
										dupes.add(drv);
										break;
									}
								}
						}
					}
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
								else
								{
									//If there is a more sutable place for the item, send it there.
									//A place it sutable if this has a filter with no items in it and the other has a filter with that item in it and it's not on not mode
									boolean moved = false;
									for (IDriver drv : driverList)
									{
										if (drv != null && drv != driver)
										{
											ItemFilter flt = drv.getItemFilter();
											if (flt != null)
											{
												if (!flt.not && flt.stacks.size() > 0)
												{
													
													for (ItemStack s : flt.stacks)
													{
														if (s.isItemEqual(stack))
														{
															moveQueue.push(new StackInfo(driver, i, drv));
															moved = true;
															break;
														}
													}
												}
											}
										}
										if (moved) break;
									}
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
		
		if (cooldown < 1)
		{
			//Process 4 items on the move queue
			for (int i = 0; i<4; i++)
			{
				if (!moveQueue.isEmpty())
				{
					//Find a place for the new item
					StackInfo info = moveQueue.pop();
					if (info.dest != null)
					{
						ItemStack item = info.driver.getStack(info.slot);
						if (item != null)
						{
							if (info.dest.addItemStack(item))
							{
								cooldown += item.stackSize/16;
								info.driver.setStack(info.slot, null);
							}
						}
					}
					else
					{
						/*
						 * Priority:
						 * First check for any filter with this item
						 * Then check for any filter that is in not mode without this item
						 * Then check for any filter that is not in not mode with no items
						 */
						//TODO: Priority
						for (IDriver driver : driverList)
						{
							if (driver != null)
							{
								//For right now, we will only check for things without anything in the filter
								ItemFilter filter = driver.getItemFilter();
								if (filter != null)
								{
									if (filter.matchesFilter(info.driver.getStack(info.slot)))
									{
										//Move the item here
										//System.out.println("Found place");
										ItemStack item = info.driver.getStack(info.slot);
										if (driver.addItemStack(item.copy()))
										{
											cooldown += item.stackSize/16;
											info.driver.setStack(info.slot, null);
											break;
										}
										else
										{
											info.driver.setStack(info.slot, item);
										}
									}
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
		else
		{
			cooldown--;
		}
	}
	
	private class StackInfo
	{
		public IDriver driver;
		public int slot;
		public IDriver dest;
		public StackInfo(IDriver d, int s)
		{
			driver = d;
			slot = s;
		}
		public StackInfo(IDriver d, int s, IDriver dest)
		{
			this(d,s);
			this.dest = dest;
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
