package ds.mods.progsys.net;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryInfo {
	public ItemStack[] stacks;
	public int size;
	
	public InventoryInfo(int s)
	{
		size = s;
		stacks = new ItemStack[s];
	}
	
	public InventoryInfo(IInventory inv)
	{
		this(inv.getSizeInventory());
		for (int i=0; i<size; i++)
		{
			stacks[i] = inv.getStackInSlot(i);
		}
	}
}
