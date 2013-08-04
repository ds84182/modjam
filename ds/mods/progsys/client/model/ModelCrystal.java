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
		for (Face f : model.groupObjects.get(0).faces)
		{
			GL11.glBegin(f.vertices.length == 4 ? GL11.GL_QUADS : GL11.GL_TRIANGLES);
			
			for (Vertex v : f.vertices)
			{
				GL11.glVertex3f(v.x, v.y, v.z);
			}
			
			GL11.glEnd();
		}
	}
}
