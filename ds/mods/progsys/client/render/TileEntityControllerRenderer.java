package ds.mods.progsys.client.render;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import ds.mods.progsys.tile.TileEntityController;

public class TileEntityControllerRenderer extends TileEntitySpecialRenderer {
	private int zLevel = 0;

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
			double d2, float f) {
		TileEntityController tile = (TileEntityController) tileentity;
		if (tile.showHolo && tile.worldObj.getBlockMaterial(tile.xCoord, tile.yCoord+1, tile.zCoord) == Material.air)
		{
			GL11.glPushMatrix();
			{
				GL11.glTranslated(d0, d1, d2);
				GL11.glColor4f(0.1F, 0.8F, 0.3F, 0.5F);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glEnable (GL11.GL_BLEND);
				GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				drawHolo((TileEntityController) tileentity);
				GL11.glDisable (GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glPushMatrix();
				{
					GL11.glTranslated(-1D, 4.5D, 0.5001D);
					GL11.glScalef(1/64F, -1/64F, 1/64F);
					drawHoloContentBG(tile);
					GL11.glTranslated(0D, 0D, 0.0001D);
					drawHoloContent((TileEntityController)tileentity);
				}
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				{
					GL11.glRotatef(180F, 0F, 1F, 0F);
					GL11.glTranslated(-2D, 4.5D, -0.4999D);
					GL11.glScalef(1/64F, -1/64F, 1/64F);
					drawHoloContent((TileEntityController)tileentity);
					drawHoloContentBG(tile);
					GL11.glTranslated(0D, 0D, -0.0001D);
				}
				GL11.glPopMatrix();
				GL11.glEnable(GL11.GL_LIGHTING);
			}
			GL11.glPopMatrix();
		}
	}
	
	private void drawHolo(TileEntityController tile)
	{
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex3f(-1F, 1.5F, 0.5F);
		GL11.glVertex3f(2F, 1.5F, 0.5F);
		GL11.glVertex3f(2F, 4.5F, 0.5F);
		GL11.glVertex3f(-1F, 4.5F, 0.5F);
		
		GL11.glEnd();
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex3f(-1F, 1.5F, 0.5F);
		GL11.glVertex3f(-1F, 4.5F, 0.5F);
		GL11.glVertex3f(2F, 4.5F, 0.5F);
		GL11.glVertex3f(2F, 1.5F, 0.5F);
		
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES);
		
		GL11.glVertex3f(-1F, 1.5F, 0.5F);
		GL11.glVertex3f(0.5F, 1F, 0.5F);
		GL11.glVertex3f(2F, 1.5F, 0.5F);
		
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES);
		
		GL11.glVertex3f(-1F, 1.5F, 0.5F);
		GL11.glVertex3f(2F, 1.5F, 0.5F);
		GL11.glVertex3f(0.5F, 1F, 0.5F);
		
		GL11.glEnd();
	}
	
	private void drawHoloContentBG(TileEntityController tile)
	{
		GL11.glColor3f(0F,0F,0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		//drawTexturedModalRect(0,0,0,0,128,8);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	private void drawHoloContent(TileEntityController tile)
	{
		GL11.glPushMatrix();
		GL11.glScalef(2F, 2F, 2F);
		this.getFontRenderer().drawString("Controller", 0, 0, 0xFFFFFF, false);
		GL11.glPopMatrix();
		
		String status = "";
		if (tile.net.tileMap.size() > 1)
			status = "Controller is running...";
		else
			status = "Controller is offline...";
		this.getFontRenderer().drawString(status, 3, 16, 0xFFFFFF, false);
		
		for (int i = 0; i<7; i++)
		{
			String str = tile.conflictMap.get(ForgeDirection.values()[i]);
			if (str == null)
				this.getFontRenderer().drawString("No conflicts on side "+ForgeDirection.values()[i].name(), 3, 24+(i*10), 0xFFFFFF, false);
			else
				this.getFontRenderer().drawString(str+" on side "+ForgeDirection.values()[i].name(), 3, 24+(i*10), 0xFF0000, false);
		}
	}
	
	/**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
	public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + par6) * f1));
        tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + par6) * f1));
        tessellator.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + par5) * f), (double)((float)(par4 + 0) * f1));
        tessellator.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + 0) * f), (double)((float)(par4 + 0) * f1));
        tessellator.draw();
    }

}
