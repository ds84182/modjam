package ds.mods.progsys.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import ds.mods.progsys.client.model.ModelWire;
import ds.mods.progsys.tile.TileEntityWire;

public class TileEntityWireRenderer extends TileEntitySpecialRenderer {

	ModelWire model = new ModelWire();
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
			double d2, float f) {
		GL11.glPushMatrix();
		
		TileEntityWire wire = (TileEntityWire)tileentity;
		model.renderPart("WireBase");
		//TODO: Render more!
		
		GL11.glPopMatrix();
	}

}
