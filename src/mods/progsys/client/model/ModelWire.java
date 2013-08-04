package ds.mods.progsys.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelWire extends ModelBase {
	private IModelCustom model;
	
	public ModelWire()
	{
		model = AdvancedModelLoader.loadModel("/assets/progsys/models/Wire.obj");
	}

	public void renderPart(String part)
	{
		model.renderPart(part);
	}

}
