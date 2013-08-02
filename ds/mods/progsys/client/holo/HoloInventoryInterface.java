package ds.mods.progsys.client.holo;

import org.lwjgl.opengl.GL11;

import ds.mods.progsys.tile.TileEntityInventoryInterface;

public class HoloInventoryInterface extends HoloGui {
	TileEntityInventoryInterface tile;
	public HoloInventoryInterface(TileEntityInventoryInterface t)
	{
		tile = t;
	}

	@Override
	public void drawBackground() {
		
	}

	@Override
	public void draw() {
		GL11.glPushMatrix();
		GL11.glScalef(2F, 2F, 2F);
		drawString(font,"Inventory", 0, 0, 0xFFFFFF);
		drawString(font,"Interface", 0, 8, 0xFFFFFF);
		GL11.glPopMatrix();
		if (tile.driver.inv != null)
		{
			int numOfItems = 0;
			for (int i = 0; i<tile.driver.inv.getSizeInventory(); i++)
			{
				//tile.driver.inv.
				if (tile.driver.inv.getStackInSlot(i) != null) numOfItems++;
			}
			drawString(font,"Items: "+numOfItems+"/"+tile.driver.inv.getSizeInventory(), 3, 32, 0xFFFFFF);
		}
	}

	@Override
	public float[] getHoloColors() {
		return null;
	}

}
