package ds.mods.progsys.items;

import ds.mods.progsys.ProgSys;
import net.minecraft.item.Item;

public class ItemPS extends Item {

	public ItemPS(int par1) {
		super(par1-256);
		this.setCreativeTab(ProgSys.tab);
	}

}
