package ds.mods.progsys.wirednet.drivers;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import ds.mods.progsys.wirednet.EnumDriverType;
import ds.mods.progsys.wirednet.IDriver;
import ds.mods.progsys.wirednet.ItemFilter;

public class ItemDriver implements IDriver {
	public IInventory inv;
	public ItemFilter filter = new ItemFilter();
	
	public ItemDriver(IInventory i)
	{
		inv = i;
	}

	@Override
	public EnumDriverType getType() {
		return EnumDriverType.ITEM;
	}

	@Override
	public boolean hasFilters() {
		return true;
	}

	@Override
	public int getSize() {
		return inv != null ? inv.getSizeInventory() : 0;
	}

	@Override
	public ItemStack getStack(int slot) {
		return inv != null ? inv.getStackInSlot(slot) : null;
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		inv.setInventorySlotContents(slot, stack);
		inv.onInventoryChanged();
	}

	@Override
	public ItemStack getStackAndRemove(int slot) {
		ItemStack s = this.getStack(slot);
		this.setStack(slot, null);
		return s;
	}

	@Override
	public ItemFilter getItemFilter() {
		return filter;
	}
	
	@Override
	public boolean addItemStack(ItemStack stack)
	{
		return this.addItemStack(stack,-1);
	}
	
	public boolean addItemStack(ItemStack stack,int not)
	{
		//Space check
		for (int i = 0; i<this.getSize(); i++)
		{
			if (i != not)
			{
				ItemStack item = this.getStack(i);
				if (item == null)
				{
					this.setStack(i, stack);
					return true;
				}
				else if (item.areItemStackTagsEqual(item, stack) && item.isItemEqual(stack))
				{
					if (item.stackSize+stack.stackSize < item.getMaxStackSize())
					{
						item.stackSize+=stack.stackSize;
						this.setStack(i, item);
						return true;
					}
					else if (item.stackSize < item.getMaxStackSize())
					{
						ItemStack copy = stack.copy();
						copy.stackSize = ((item.stackSize+stack.stackSize)-item.getMaxStackSize());
						if (this.addItemStack(copy,i))
						{
							item.stackSize=item.getMaxStackSize();
							this.setStack(i, item);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public int getCapacity() {
		return 0;
	}

	@Override
	public FluidStack getStack() {
		return null;
	}

	@Override
	public FluidTank getTank() {
		return null;
	}

	@Override
	public FluidStack[] getFluidFilters() {
		return null;
	}

}
