package ds.mods.progsys.net;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class PacketInventoryInterfaceState implements
		IPacket<InventoryInterfaceState> {

	@Override
	public byte[] writePacket(InventoryInterfaceState obj) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		return out.toByteArray();
	}

	@Override
	public InventoryInterfaceState readPacket(byte[] data) {
		// TODO Auto-generated method stub
		return null;
	}

}
