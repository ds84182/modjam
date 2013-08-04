package ds.mods.progsys.client.model;

import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class ModelCrystal {
	private WavefrontObject model = (WavefrontObject) AdvancedModelLoader.loadModel("/assets/progsys/models/crystal.obj");
	
	public void render()
	{
		model.renderAll();
	}
	
	public void renderTessellator()
	{
		//Render this while the tesslator is tesslating!
		model.groupObjects.get(0).render(Tessellator.instance);
	}
}
