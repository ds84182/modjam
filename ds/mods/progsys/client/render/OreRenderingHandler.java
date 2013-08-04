package ds.mods.progsys.client.render;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import ds.mods.progsys.ProgSys;
import ds.mods.progsys.client.model.ModelCrystal;

public class OreRenderingHandler implements ISimpleBlockRenderingHandler {
	
	ModelCrystal crystal = new ModelCrystal();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		World w = Minecraft.getMinecraft().theWorld;
		render(0,0,0,w.getSeed());
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		World w = Minecraft.getMinecraft().theWorld;
		render(x,y,z,w.getSeed());
		return false;
	}
	
	public void render(int x, int y, int z, long seed)
	{
		/*
		 * Equations:
		 * Random generator with Math.pow(x*y,z)+WorldSeed as the seed
		 * 
		 * Crystals: random number between 8 and 32
		 * Face: random number between 0 and 5
		 * Position: random number between 0 and 255 (casted to x y)
		 * Rotation: 2 random numbers between 0 and 255
		 */
		Random rand = new Random((long)Math.pow(x*y,z)*seed);
		int numCrystals = rand.nextInt(24)+8;
		for (int i = 0; i<numCrystals; i++)
		{
			crystal.renderTessellator();
		}
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return ProgSys.proxy.CrystalRenderID;
	}

}
