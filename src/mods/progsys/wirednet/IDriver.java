package ds.mods.progsys.wirednet;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public interface IDriver {
	public EnumDriverType getType();
	public boolean hasFilters();
	
	//Item driver only
	public int getSize();
	public ItemStack getStack(int slot);
	public void setStack(int slot, ItemStack stack);
	public ItemStack getStackAndRemove(int slot);
	public ItemFilter getItemFilter();
	public boolean addItemStack(ItemStack stack);
	
	//Fluid driver only
	//TBH, I have never used the fluid system before.
	//Also, fluids are not going to be added in the real version, I don't even know how to test them
	public int getCapacity();
	public FluidStack getStack();
	public FluidTank getTank();
	public FluidStack[] getFluidFilters();
	
}
