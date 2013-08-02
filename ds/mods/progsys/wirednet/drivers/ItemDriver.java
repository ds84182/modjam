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
		return inv.getSizeInventory();
	}

	@Override
	public ItemStack getStack(int slot) {
		return inv.getStackInSlot(slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		inv.setInventorySlotContents(slot, stack);
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
