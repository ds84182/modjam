package ds.mods.progsys.net;

public class PacDispat {
	public static IPacket[] packetTypes;
	
	public static void initPacketStuff()
	{
		packetTypes = new IPacket[]{
				new PacketInventoryInfo(),
		};
	}
}
