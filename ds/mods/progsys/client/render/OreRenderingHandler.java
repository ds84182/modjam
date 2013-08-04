package ds.mods.progsys.client.render;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import ds.mods.progsys.ProgSys;
import ds.mods.progsys.client.model.ModelCrystal;

public class OreRenderingHandler implements ISimpleBlockRenderingHandler {
	
	ModelCrystal crystal = new ModelCrystal();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		GL11.glDisable(GL11.GL_LIGHTING);
		World w = Minecraft.getMinecraft().theWorld;
		Icon stone = Block.stone.getIcon(0, 0);
		Tessellator.instance.startDrawingQuads();
		Tessellator.instance.setColorOpaque(255, 255, 255);
		renderer.renderFaceXNeg(Block.stone, 0,0,0 , stone);
		renderer.renderFaceYNeg(Block.stone, 0,0,0 , stone);
		renderer.renderFaceZNeg(Block.stone, 0,0,0 , stone);
		renderer.renderFaceXPos(Block.stone, 0,0,0 , stone);
		renderer.renderFaceYPos(Block.stone, 0,0,0 , stone);
		renderer.renderFaceZPos(Block.stone, 0,0,0 , stone);
		Tessellator.instance.draw();
		render(1,1,1,w.getSeed());
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		//renderer.renderBlockAllFaces(Block.stone, x, y, z);
		World w = Minecraft.getMinecraft().theWorld;
		render(x,y,z,w.getSeed());
		return true;
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
		GL11.glColor3f(0F, 0.64F, 0.022558F);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator tess = Tessellator.instance;
		GL11.glTranslated(tess.xOffset+x+0.5D,tess.yOffset+y+0.5D,tess.zOffset+z+0.5D);
		//GL11.glScaled(1D, -1D, 1D);
		double[][] rots = {
				{0D,0D,0D},
				{0D,0D,0D},
				{0D,0D,0D},
				{0D,0D,0D},
				{0D,0D,0D},
				{0D,0D,0D},
		};
		for (int i = 0; i<numCrystals; i++)
		{
			GL11.glPushMatrix();
			int side = rand.nextInt(6);
			GL11.glRotated(90D, rots[side][0], rots[side][1], rots[side][2]);
			int pos = rand.nextInt(255);
			int cy = (pos%16);
			int cx = (pos-cy)/16;
			GL11.glTranslated(0D, 0.5D, 0D);
			GL11.glTranslated((cx-8)/16D,0D,(cy-8)/16D);
			crystal.render();
			GL11.glPopMatrix();
		}
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
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
