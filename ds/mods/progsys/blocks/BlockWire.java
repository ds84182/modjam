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

public class BlockWire extends Block {

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
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		TileEntity tile = par1World.getBlockTileEntity(par2, par3, par4);
		if (tile instanceof IOnPlace)
		{
			((IOnPlace)tile).onPlace(par5EntityLivingBase, par6ItemStack);
		}
	}
	
}
