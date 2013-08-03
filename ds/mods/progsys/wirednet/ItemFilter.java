package ds.mods.progsys.wirednet;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class ItemFilter {
	public boolean not = false;
	public ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
	
	public boolean matchesFilter(ItemStack stack)
	{
		if (stack == null)
			return false;
		if (!not & stacks.size() == 0)
			return true;
		if (not & stacks.size() != 0)
		{
			for (ItemStack other : stacks)
			{
				if (stack.isItemEqual(other))
				{
					return false;
				}
			}
			return true;
		}
		if (!not & stacks.size() != 0)
		{
			for (ItemStack other : stacks)
			{
				if (stack.isItemEqual(other))
				{
					return true;
				}
			}
		}
		return false;
	}
}
