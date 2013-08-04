package ds.mods.progsys.client.holo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public abstract class HoloGui extends Gui {
	public FontRenderer font;
	
	protected HoloGui()
	{
		font = Minecraft.getMinecraft().fontRenderer;
	}
	
	public abstract void drawBackground(boolean back);
	public abstract void draw(boolean back);
	public abstract float[] getHoloColors();
	
	public void drawString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
    {
        par1FontRenderer.drawString(par2Str, par3, par4, par5);
    }
}
