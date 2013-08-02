package ds.mods.progsys.tile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IOnPlace {
	public void onPlace(EntityLivingBase entity, ItemStack stack);
}
