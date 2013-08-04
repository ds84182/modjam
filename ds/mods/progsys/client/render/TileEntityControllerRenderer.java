package ds.mods.progsys.client.render;

import java.nio.FloatBuffer;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import ds.mods.progsys.client.holo.HoloController;
import ds.mods.progsys.client.holo.HoloUtils;
import ds.mods.progsys.client.model.ModelCrystal;
import ds.mods.progsys.tile.TileEntityController;

public class TileEntityControllerRenderer extends TileEntitySpecialRenderer {
	private ModelCrystal model = new ModelCrystal();
	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(4);
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
			double d2, float f) {
		TileEntityController tile = (TileEntityController) tileentity;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPushMatrix();
		{
			GL11.glTranslated(d0+0.5D, d1+0.5D, d2+1D);
			GL11.glRotated(180D, 0.132113D, 0.4123D, 0.5931D);
			GL11.glColor3f(0F, 0.64F, 0.022558F);
			model.render();
		}
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		if (tile.showHolo)
		{
			GL11.glPushMatrix();
			{
				GL11.glTranslated(d0, d1, d2);
				if (tile.gui == null)
					tile.gui = new HoloController(tile);
				HoloUtils.renderHolo(tile.gui);
			}
			GL11.glPopMatrix();
		}
	}
	
	private static FloatBuffer setColorBuffer(float par0, float par1, float par2, float par3)
    {
        colorBuffer.clear();
        colorBuffer.put(par0).put(par1).put(par2).put(par3);
        colorBuffer.flip();
        return colorBuffer;
    }
	
	public static void enableStandardItemLighting(float x, float y, float z)
    {
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_LIGHT0);
        GL11.glEnable(GL11.GL_LIGHT1);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE);
        float f = 1F;
        float f2 = 1F;
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, setColorBuffer(x,y,z, 0.0F));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, setColorBuffer(1F, 1F, 1F, 1.0F));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, setColorBuffer(0.1F, 0.1F, 0.1F, 1.0F));
        GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, setColorBuffer(f2, f2, f2, 1.0F));
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, setColorBuffer(x,y,z, 0.0F));
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, setColorBuffer(1F, 1F, 1F, 1.0F));
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, setColorBuffer(0.1F, 0.1F, 0.1F, 1.0F));
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPECULAR, setColorBuffer(f2, f2, f2, 1.0F));
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, setColorBuffer(f, f, f, 1.0F));
    }
	
	public static void disableStandardItemLighting()
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_LIGHT0);
        GL11.glDisable(GL11.GL_LIGHT1);
        GL11.glDisable(GL11.GL_COLOR_MATERIAL);
    }

}
