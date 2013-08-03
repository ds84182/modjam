package ds.mods.progsys.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.mods.progsys.tile.IOnPlace;
import ds.mods.progsys.tile.IOnRemove;
import ds.mods.progsys.tile.TileEntityController;

public class BlockController extends Block {
	@SideOnly(Side.CLIENT)
	public Icon blank;

	public BlockController(int par1, Material par2Material) {
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
		return new TileEntityController();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("progsys:controller");
		blank = par1IconRegister.registerIcon("progsys:blank");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return par1 == 0 || par1 == 1 ? blockIcon : blank;
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

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	@Override
	public int getRenderType() {
		return 0;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		TileEntityController tile = (TileEntityController) par1World.getBlockTileEntity(par2, par3, par4);
		tile.showHolo = !tile.showHolo;
		return true;
	}

}
