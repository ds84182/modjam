package ds.mods.progsys.net;

import java.lang.reflect.Method;

import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacDispat {
	public static IPacket[] packetTypes;
	
	public static void initPacketStuff()
	{
		packetTypes = new IPacket[]{
				new PacketInventoryInfo(),
				new PacketInventoryInterfaceState(),
		};
	}
	
	private static int findPacketType(Object obj)
	{
		for (int i = 0; i<packetTypes.length; i++)
		{
			IPacket p = packetTypes[i];
			for (Method m : p.getClass().getMethods())
			{
				if (m.getParameterTypes().length == 1 && m.getName().equals("writePacket"))
				{
					if (m.getParameterTypes()[0].equals(obj.getClass()))
					{
						return i;
					}
				}
			}
		}
		return 0;
	}
	
	private static Packet250CustomPayload createPacket(int type, byte[] data)
	{
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "ProgSys";
		packet.data = new byte[data.length+1];
		packet.data[0] = (byte) type;
		for (int i = 0; i<data.length; i++)
		{
			packet.data[i+1] = data[i];
		}
		return packet;
	}
	
	public static void sendPacketToPlayer(Object obj, Player player)
	{
		PacketDispatcher.sendPacketToPlayer(createPacket(findPacketType(obj),packetTypes[findPacketType(obj)].writePacket(obj)), player);
	}
	
	public static void sendPacketToAllPlayers(Object obj)
	{
		PacketDispatcher.sendPacketToAllPlayers(createPacket(findPacketType(obj),packetTypes[findPacketType(obj)].writePacket(obj)));
	}
	
	public static void sendPacketToDimension(Object obj, int dimID)
	{
		PacketDispatcher.sendPacketToAllInDimension(createPacket(findPacketType(obj),packetTypes[findPacketType(obj)].writePacket(obj)), dimID);
	}
	
	public static Object recievePacket(byte[] data)
	{
		int type = data[0];
		byte[] dat = new byte[data.length-1];
		for (int i = 0; i<dat.length; i++)
		{
			dat[i] = data[i+1];
		}
		return packetTypes[type].readPacket(dat);
	}
}
