package ds.mods.progsys.wirednet.netbase;

import ds.mods.progsys.wirednet.EnumNBType;
import ds.mods.progsys.wirednet.IDriver;
import ds.mods.progsys.wirednet.INetworkBase;
import ds.mods.progsys.wirednet.Vector3;

public class WireNetworkBase implements INetworkBase {

Vector3 pos;
	
	public WireNetworkBase(Vector3 position)
	{
		pos = position;
	}

	@Override
	public Vector3 getPosition() {
		return pos;
	}

	@Override
	public EnumNBType getType() {
		return EnumNBType.WIRE;
	}

	@Override
	public IDriver getInterfaceDriver() {
		return null;
	}

}
