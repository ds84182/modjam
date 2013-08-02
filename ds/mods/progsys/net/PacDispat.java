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
		packet.channel = "";
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
}
