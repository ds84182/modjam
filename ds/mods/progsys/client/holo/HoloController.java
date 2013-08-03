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
		int y = 16;
		String status = "";
		if (tile.net.tileMap.size() > 1)
			status = "Controller is running...";
		else
			status = "Controller is offline...";
		drawString(font,status, 3, y, 0xFFFFFF);
		y+=8;
		
		for (int i = 0; i<7; i++)
		{
			String str = tile.conflictMap.get(ForgeDirection.values()[i]);
			if (str != null)
			{
				drawString(font,str+" on side "+ForgeDirection.values()[i].name(), 0, y+(i*10), 0xFF0000);
				y+=8;
			}
		}
		if (tile.controller.moveQueue.size() != 0)
		{
			drawString(font,"Moving "+tile.controller.moveQueue.size()+" items...",0,y,0xFFFFFF);
			y+=8;
		}
		drawString(font,"Managing "+tile.controller.driverList.size()+" drivers...",0,y,0xFFFFFF);
		y+=8;
	}

	@Override
	public float[] getHoloColors() {
		return null; //Returning null will default to hologreen or the config
	}

}
