package ds.mods.progsys.client.holo;

import ds.mods.progsys.tile.TileEntityInventoryInterface;

public class HoloInventoryInterface extends HoloGui {
	TileEntityInventoryInterface tile;
	public HoloInventoryInterface(TileEntityInventoryInterface t)
	{
		tile = t;
	}

	@Override
	public void drawBackground() {
		
	}

	@Override
	public void draw() {
		
	}

	@Override
	public float[] getHoloColors() {
		return null;
	}

}
