package ds.mods.progsys.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import ds.mods.progsys.client.model.ModelCrystal;

public class CrystalItemRenderer implements IItemRenderer {
	ModelCrystal model = new ModelCrystal();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type)
		{
		case ENTITY:
			render(0.5D,0.5D,2D);
			break;
		case EQUIPPED:
			render(0,0,0);
			break;
		case EQUIPPED_FIRST_PERSON:
			render(0,0,0);
			break;
		case FIRST_PERSON_MAP:
			break;
		case INVENTORY:
			render(0.5D,0.5D,2D);
			break;
		default:
			break;
		}
	}
	
	private void render(double x, double y, double z)
	{
		GL11.glPushMatrix();
		
		GL11.glTranslated(x, y, z);
		GL11.glColor3f(0F, 0.64F, 0.022558F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		model.render();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glPopMatrix();
	}

}
