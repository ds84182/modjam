package ds.mods.progsys.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.Face;
import net.minecraftforge.client.model.obj.Vertex;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class ModelCrystal {
	private WavefrontObject model = (WavefrontObject) AdvancedModelLoader.loadModel("/assets/progsys/models/crystal.obj");
	
	public void render()
	{
		model.renderAll();
	}
}
