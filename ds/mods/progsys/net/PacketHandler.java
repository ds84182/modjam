package ds.mods.progsys.net;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import ds.mods.progsys.ProgSys;
import ds.mods.progsys.tile.TileEntityInventoryInterface;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		Object obj = PacDispat.recievePacket(packet.data);
		if (obj instanceof InventoryInfo)
		{
			InventoryInfo info = (InventoryInfo)obj;
			World w = ProgSys.proxy.getClientWorld();
			TileEntityInventoryInterface tile = (TileEntityInventoryInterface) w.getBlockTileEntity(info.vec.x, info.vec.y, info.vec.z);
			tile.invInfo = info;
		}
	}

}
