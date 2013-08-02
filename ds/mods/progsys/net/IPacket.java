package ds.mods.progsys.net;

public interface IPacket<T> {
	public byte[] writePacket(T obj);
	public T readPacket(byte[] data);
}
