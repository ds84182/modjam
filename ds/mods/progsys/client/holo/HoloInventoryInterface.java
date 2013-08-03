package ds.mods.progsys.client.holo;

import net.minecraft.client.renderer.entity.RenderItem;

import org.lwjgl.opengl.GL11;

import ds.mods.progsys.tile.TileEntityInventoryInterface;

public class HoloInventoryInterface extends HoloGui {
	TileEntityInventoryInterface tile;
	RenderItem render;
	public HoloInventoryInterface(TileEntityInventoryInterface t)
	{
		tile = t;
		render = new RenderItem(){

			@Override
			public boolean shouldBob() {
				return false;
			}
			
		};
	}

	@Override
	public void drawBackground() {
		//We will render all the items at once, but firs we need to find a number that is square from the stack number
		if (tile.invInfo != null)
		{
			
		}
	}

	@Override
	public void draw() {
		GL11.glPushMatrix();
		GL11.glScalef(2F, 2F, 2F);
		drawString(font,"Inventory", 0, 0, 0xFFFFFF);
		drawString(font,"Interface", 0, 8, 0xFFFFFF);
		GL11.glPopMatrix();
		if (tile.invInfo != null)
		{
			int numOfItems = 0;
			for (int i = 0; i<tile.invInfo.size; i++)
			{
				//tile.driver.inv.
				if (tile.invInfo.stacks[i] != null) numOfItems++;
			}
			drawString(font,"Items: "+numOfItems+"/"+tile.invInfo.size, 3, 32, 0xFFFFFF);
			int siz = tile.invInfo.size;
			double sq = Math.sqrt(siz);
			while (Math.floor(sq) != sq)
			{
				siz++;
				sq = Math.sqrt(siz);
			}
			//We will start item rendering at 64, the size of the gui is 3*64x3*64
			//We need to find the size of the content that will leave a 2px space between elements and a 1px space between screen edges
			int maxX = 3*64;
			int maxY = (3*64)-64;
			maxX--;
			maxY--;
			maxX -= (sq-2)*2;
			maxY -= (sq-2)*2;
			int w = (int) (maxX / sq);
			int h = (int) (maxY / sq);
			int screenX = 1;
			int screenY = 65;
			//System.out.println(w+","+h);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			for (int cx = 0; cx<sq; cx++)
			{
				for (int cy = 0; cy<sq; cy++)
				{
					this.drawTexturedModalRect(cx*w, cy*h, 0, 0, w, h);
				}
			}
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
	}

	@Override
	public float[] getHoloColors() {
		return null;
	}

}
