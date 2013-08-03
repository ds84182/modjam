package ds.mods.progsys.client.holo;

import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import ds.mods.progsys.tile.TileEntityController;

public class HoloController extends HoloGui {
	TileEntityController tile;
	
	public HoloController(TileEntityController t)
	{
		super();
		tile = t;
	}

	@Override
	public void drawBackground(boolean side) {
		
	}

	@Override
	public void draw(boolean side) {
		GL11.glPushMatrix();
		GL11.glScalef(2F, 2F, 2F);
		drawString(font,"Controller", 0, 0, 0xFFFFFF);
		GL11.glPopMatrix();
		
		String status = "";
		if (tile.net.tileMap.size() > 1)
			status = "Controller is running...";
		else
			status = "Controller is offline...";
		drawString(font,status, 3, 16, 0xFFFFFF);
		
		for (int i = 0; i<7; i++)
		{
			String str = tile.conflictMap.get(ForgeDirection.values()[i]);
			if (str == null)
				drawString(font,"No conflicts on side "+ForgeDirection.values()[i].name(), 3, 24+(i*10), 0xFFFFFF);
			else
				drawString(font,str+" on side "+ForgeDirection.values()[i].name(), 3, 24+(i*10), 0xFF0000);
		}
	}

	@Override
	public float[] getHoloColors() {
		return null; //Returning null will default to hologreen or the config
	}

}
