package ds.mods.progsys.net;

import net.minecraft.entity.player.EntityPlayer;
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
			if (tile != null)
			{
				tile.invInfo = info;
			}
		}
		else if (obj instanceof InventoryInterfaceState)
		{
			InventoryInterfaceState state = (InventoryInterfaceState)obj;
			EntityPlayer realPlayer = (EntityPlayer)player;
			World w = realPlayer.worldObj;
			TileEntityInventoryInterface tile = (TileEntityInventoryInterface) w.getBlockTileEntity(state.pos.x, state.pos.y, state.pos.z);
			if (tile != null)
			{
				//Extract state
				tile.showHolo = state.showHolo;
				if (tile.driver != null)
				{
					tile.driver.filter = state.filter;
				}
			}
		}
	}

}
