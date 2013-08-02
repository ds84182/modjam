package ds.mods.progsys.client.holo;

import org.lwjgl.opengl.GL11;

import ds.mods.progsys.tile.TileEntityController;

public class HoloUtils {
	public static void renderHolo(HoloGui gui)
	{
		float[] color = gui.getHoloColors();
		if (color != null)
			GL11.glColor4f(color[0], color[1], color[2], 0.5F);
		else
			GL11.glColor4f(0.1F, 0.8F, 0.3F, 0.5F);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable (GL11.GL_BLEND);
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		drawHolo();
		GL11.glDisable (GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPushMatrix();
		{
			GL11.glTranslated(-1D, 4.5D, 0.5001D);
			GL11.glScalef(1/64F, -1/64F, 1/64F);
			gui.drawBackground();
			GL11.glTranslated(0D, 0D, 0.0001D);
			gui.draw();
		}
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		{
			//GL11.glRotatef(180F, 0F, 1F, 0F);
			GL11.glTranslated(2D, 4.5D, 0.4999D);
			GL11.glScalef(-1/64F, -1/64F, 1/64F);
			gui.drawBackground();
			GL11.glTranslated(0D, 0D, -0.0001D);
			gui.draw();
		}
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	private static void drawHolo()
	{
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex3f(-1F, 1.5F, 0.5F);
		GL11.glVertex3f(-1F, 4.5F, 0.5F);
		GL11.glVertex3f(2F, 4.5F, 0.5F);
		GL11.glVertex3f(2F, 1.5F, 0.5F);
		
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES);
		
		GL11.glVertex3f(-1F, 1.5F, 0.5F);
		GL11.glVertex3f(2F, 1.5F, 0.5F);
		GL11.glVertex3f(0.5F, 1F, 0.5F);
		
		GL11.glEnd();
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glVertex3f(-1F, 1.5F, 0.5F);
		GL11.glVertex3f(2F, 1.5F, 0.5F);
		GL11.glVertex3f(2F, 4.5F, 0.5F);
		GL11.glVertex3f(-1F, 4.5F, 0.5F);
		
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES);
		
		GL11.glVertex3f(-1F, 1.5F, 0.5F);
		GL11.glVertex3f(0.5F, 1F, 0.5F);
		GL11.glVertex3f(2F, 1.5F, 0.5F);
		
		GL11.glEnd();
	}
}
