package ds.mods.progsys.wirednet.netbase;

import ds.mods.progsys.wirednet.EnumNBType;
import ds.mods.progsys.wirednet.IDriver;
import ds.mods.progsys.wirednet.INetworkBase;
import ds.mods.progsys.wirednet.Vector3;

public class ControllerNetworkBase implements INetworkBase {
	
	Vector3 pos;
	
	public ControllerNetworkBase(Vector3 position)
	{
		pos = position;
	}

	@Override
	public Vector3 getPosition() {
		return pos;
	}

	@Override
	public EnumNBType getType() {
		return EnumNBType.CONTROLLER;
	}

	@Override
	public IDriver getInterfaceDriver() {
		return null;
	}

}
