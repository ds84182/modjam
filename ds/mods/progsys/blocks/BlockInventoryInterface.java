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
import ds.mods.progsys.tile.TileEntityInventoryInterface;

public class BlockInventoryInterface extends Block {
	@SideOnly(Side.CLIENT)
	public Icon socket;

	public BlockInventoryInterface(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2) {
		return par1 == par2 ? socket : blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		socket = par1IconRegister.registerIcon("progsys:invsocket");
		this.blockIcon = par1IconRegister.registerIcon("progsys:iteminterface");
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		TileEntityInventoryInterface tile = (TileEntityInventoryInterface) par1World.getBlockTileEntity(par2, par3, par4);
		tile.showHolo = !tile.showHolo;
		return true;
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
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityInventoryInterface();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
