package ds.mods.progsys.wirednet;

import net.minecraftforge.common.ForgeDirection;

public class Vector3 {
	public int x;
	public int y;
	public int z;
	
	public Vector3()
	{
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vector3(int X, int Y, int Z)
	{
		x = X;
		y = Y;
		z = Z;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector3)
		{
			return obj.hashCode() == hashCode();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.x * 8976890 + this.y * 981131 + this.z; //Thanks ChunkPosition :D
	}

	@Override
	public String toString() {
		return "["+x+","+y+","+z+"]";
	}

	public Vector3 add(ForgeDirection fdir) {
		return new Vector3(x+fdir.offsetX,y+fdir.offsetY,z+fdir.offsetZ);
	}
}
