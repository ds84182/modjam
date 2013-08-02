package ds.mods.progsys.wirednet.netbase;

import ds.mods.progsys.wirednet.EnumNBType;
import ds.mods.progsys.wirednet.IDriver;
import ds.mods.progsys.wirednet.INetworkBase;
import ds.mods.progsys.wirednet.Vector3;

public class DriverNetworkBase implements INetworkBase {
	
	public IDriver driver;
	public Vector3 pos;
	
	public DriverNetworkBase(IDriver d, Vector3 p)
	{
		driver = d;
		pos = p;
	}

	@Override
	public Vector3 getPosition() {
		return null;
	}

	@Override
	public EnumNBType getType() {
		return EnumNBType.INTERFACE;
	}

	@Override
	public IDriver getInterfaceDriver() {
		return null;
	}

}
