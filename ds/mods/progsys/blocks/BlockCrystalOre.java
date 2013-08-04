package ds.mods.progsys.blocks;

import ds.mods.progsys.ProgSys;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCrystalOre extends Block {

	public BlockCrystalOre(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return ProgSys.proxy.CrystalRenderID;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}

}
