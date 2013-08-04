package ds.mods.progsys.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ds.mods.progsys.Config;
import ds.mods.progsys.ProgSys;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockCrystalOre extends BlockPS {

	public BlockCrystalOre(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("progsys:crystalOre");
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return Config.CrystalID;
	}

	@Override
	public int quantityDroppedWithBonus(int par1, Random par2Random) {
		return par2Random.nextInt(4+par1)+2;
	}

}
