package ds.mods.progsys.net;

import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class PacketInventoryInfo implements IPacket<InventoryInfo> {

	@Override
	public byte[] writePacket(InventoryInfo obj) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeInt(obj.size);
		for (ItemStack stack : obj.stacks)
		{
			try {
				Packet.writeItemStack(stack, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return out.toByteArray();
	}

	@Override
	public InventoryInfo readPacket(byte[] data) {
		ByteArrayDataInput in = ByteStreams.newDataInput(data);
		int size = in.readInt();
		InventoryInfo info = new InventoryInfo(size);
		for (int i=0; i<size; i++)
		{
			try {
				info.stacks[i] = Packet.readItemStack(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return info;
	}

}
