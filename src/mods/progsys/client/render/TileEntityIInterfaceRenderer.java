package ds.mods.progsys.client.render;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import ds.mods.progsys.client.holo.HoloInventoryInterface;
import ds.mods.progsys.client.holo.HoloUtils;
import ds.mods.progsys.tile.TileEntityInventoryInterface;

public class TileEntityIInterfaceRenderer extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
			double d2, float f) {
		TileEntityInventoryInterface tile = (TileEntityInventoryInterface) tileentity;
		if (tile.showHolo)
		{
			GL11.glPushMatrix();
			{
				GL11.glTranslated(d0, d1, d2);
				if (tile.gui == null)
					tile.gui = new HoloInventoryInterface(tile);
				HoloUtils.renderHolo(tile.gui);
			}
			GL11.glPopMatrix();
		}
	}

}
