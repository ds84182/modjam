package ds.mods.progsys.client.holo;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;

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

			@Override
			public boolean shouldSpreadItems() {
                return false;
            }

		};
		render.setRenderManager(RenderManager.instance);
	}

	@Override
	public void drawBackground(boolean back) {
		//We will render all the items at once, but firs we need to find a number that is square from the stack number
		if (tile.invInfo != null)
		{

		}
	}

	@Override
	public void draw(boolean back) {
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
			int siz = numOfItems;
			double sq = Math.sqrt(siz);
			while (Math.floor(sq) != sq)
			{
				siz++;
				sq = Math.sqrt(siz);
			}
			GL11.glPushMatrix();
			//We will start item rendering at 64, the size of the gui is 3*64x3*64
			//We need to find the size of the content that will leave a 2px space between elements and a 1px space between screen edges
			int maxX = 3*64;
			int maxY = (3*64)-64;
			maxX-=2;
			maxY-=2;
			maxX -= (sq-1);
			maxY -= (sq-1);
			int w = (int) (maxX / sq);
			int h = (int) (maxY / sq);
			int ms = Math.max(w, h);
			int screenX = 1;
			int screenY = 65;
			//System.out.println(w+","+h);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glColor4f(0.1F, 0.4F, 0.3F, 0.5F);
			int stackAt = 0;
			EntityItem item = new EntityItem(Minecraft.getMinecraft().theWorld);
			item.hoverStart = 0f;
			for (int cx = 0; cx<sq; cx++)
			{
				for (int cy = 0; cy<sq; cy++)
				{
					if (stackAt<tile.invInfo.stacks.length && tile.invInfo.stacks[stackAt] != null)
					{
						this.drawTexturedModalRect(cx*(w+2)+screenX, cy*(h+2)+screenY, 0, 0, w, h);
						GL11.glEnable(GL11.GL_TEXTURE_2D);
						GL11.glDisable(GL11.GL_BLEND);
						item.setEntityItemStack(tile.invInfo.stacks[stackAt]);
						item.getEntityItem().stackSize = 1;
						GL11.glPushMatrix();
						GL11.glScaled(64D, -64D, 1D);
						GL11.glTranslated(((cx*-(w+2))/64D)-(w/128D)+3D, ((cy*-(h+2))/64D)-(h/128D)-1.0625D-0.125D, 0.1D * (back ? 2 : -2));
						if (!(tile.invInfo.stacks[stackAt].getItem() instanceof ItemBlock))
						{
							GL11.glScaled(0.75D, 0.75D, 0.75D);
						}
						else
						{
							//If it's a stupid glasspane
							ItemBlock block = (ItemBlock)(tile.invInfo.stacks[stackAt].getItem());
							if (block.getBlockID() == Block.thinGlass.blockID || block.getBlockID() == Block.hopperBlock.blockID)
							{
								GL11.glScaled(0.5D, 0.5D, 0.5D);
							}
						}
						//Now, try to scale the item so that it perfectly fits the box
						GL11.glScaled(ms/32D,ms/32D,1D);
						if (sq == 1)
						{
							GL11.glTranslated(0D,-0.125D-(1/128D),0D);
						}
						else
						{
							GL11.glTranslated(0D,-0.125D+(1/(128D/(sq*2))),0D);
						}
						//GL11.glRotatef(180.0F, 0.0F, 0.0F, 0.0F);
						RenderItem.renderInFrame = true;
		                RenderManager.instance.renderEntityWithPosYaw(item, 0,0,0, 0.0F, 0.0F);
		                RenderItem.renderInFrame = false;
		                GL11.glPopMatrix();
						GL11.glDisable(GL11.GL_TEXTURE_2D);
						GL11.glEnable(GL11.GL_BLEND);
					}
					GL11.glColor4f(0.1F, 0.4F, 0.3F, 0.5F);
					stackAt++;
				}
			}
			GL11.glPopMatrix();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

	@Override
	public float[] getHoloColors() {
		return null;
	}

}