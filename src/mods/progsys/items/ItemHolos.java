package ds.mods.progsys.items;

import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemHolos extends ItemPS {
	public Icon pixel;
	public Icon array;

	public ItemHolos(int par1) {
		super(par1);
		this.hasSubtypes = true;
	}

	@Override
	public String getItemDisplayName(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() == 0 ? LanguageRegistry.instance().getStringLocalization("item.ds.progsys.holos.pixel.name") : LanguageRegistry.instance().getStringLocalization("item.ds.progsys.holos.array.name");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1) {
		return par1 == 0 ? pixel : array;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		pixel = par1IconRegister.registerIcon("progsys:holopixel");
		array = par1IconRegister.registerIcon("progsys:holoarray");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs,
			List par3List) {
		super.getSubItems(par1, par2CreativeTabs, par3List);
		par3List.add(new ItemStack(this,1,1));
	}

}
