package ds.mods.progsys.client.model;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelCrystal {
	private IModelCustom model = AdvancedModelLoader.loadModel("/assets/progsys/models/crystal.obj");
	
	public void render()
	{
		model.renderAll();
	}
}
