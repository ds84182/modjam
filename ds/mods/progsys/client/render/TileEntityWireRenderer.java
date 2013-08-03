package ds.mods.progsys.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import ds.mods.progsys.client.model.ModelWire;
import ds.mods.progsys.tile.TileEntityNetworkBase;
import ds.mods.progsys.tile.TileEntityWire;
import ds.mods.progsys.wirednet.Vector3;

public class TileEntityWireRenderer extends TileEntitySpecialRenderer {

	ModelWire model = new ModelWire();
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
			double d2, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(d0+0.5D, d1+0.5D, d2+0.5D);
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		TileEntityWire wire = (TileEntityWire)tileentity;
		model.renderPart("WireBase");
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
		{
			Vector3 vec = new Vector3(wire.xCoord,wire.yCoord,wire.zCoord).add(dir);
			TileEntity other = wire.worldObj.getBlockTileEntity(vec.x, vec.y, vec.z);
			if (other != null & other instanceof TileEntityNetworkBase)
			{
				model.renderPart("Wire"+dir.name().charAt(0)+dir.name().toLowerCase().substring(1));
				//System.out.println("Wire"+dir.name().charAt(0)+dir.name().toLowerCase().substring(1));
			}
		}
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}

}
