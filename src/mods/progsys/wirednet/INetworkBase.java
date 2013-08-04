package ds.mods.progsys.wirednet;

public interface INetworkBase {
	public Vector3 getPosition();
	public EnumNBType getType();
	public IDriver getInterfaceDriver();
}
