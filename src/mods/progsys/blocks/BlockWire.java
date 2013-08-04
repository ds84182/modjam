package ds.mods.progsys.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ds.mods.progsys.tile.IOnPlace;
import ds.mods.progsys.tile.IOnRemove;
import ds.mods.progsys.tile.TileEntityWire;

public class BlockWire extends BlockPS {

	public BlockWire(int par1, Material par2Material) {
		super(par1, par2Material);
	}
	
	@Override
	public boolean hasTileEntity(int meta)
	{
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World w, int meta)
	{
		return new TileEntityWire();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
}
