package ds.mods.progsys.net;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import ds.mods.progsys.wirednet.Vector3;

public class InventoryInfo {
	public ItemStack[] stacks;
	public int size;
	public Vector3 vec;
	
	public InventoryInfo(int s, Vector3 v)
	{
		size = s;
		stacks = new ItemStack[s];
		vec = v;
	}
	
	public InventoryInfo(IInventory inv, Vector3 v)
	{
		this(inv.getSizeInventory(), v);
		for (int i=0; i<size; i++)
		{
			stacks[i] = inv.getStackInSlot(i);
		}
	}
}
