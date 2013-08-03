package ds.mods.progsys.net;

import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import ds.mods.progsys.wirednet.ItemFilter;
import ds.mods.progsys.wirednet.Vector3;

public class PacketInventoryInterfaceState implements
		IPacket<InventoryInterfaceState> {

	@Override
	public byte[] writePacket(InventoryInterfaceState obj) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeBoolean(obj.showHolo);
		out.writeInt(obj.pos.x);
		out.writeInt(obj.pos.y);
		out.writeInt(obj.pos.z);
		if (obj.filter != null)
		{
			out.writeBoolean(obj.filter.not);
			out.writeInt(obj.filter.stacks.size());
			for (ItemStack stack : obj.filter.stacks)
			{
				try {
					Packet.writeItemStack(stack, out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else
		{
			out.writeBoolean(false);
			out.writeInt(-1);
		}
		return out.toByteArray();
	}

	@Override
	public InventoryInterfaceState readPacket(byte[] data) {
		ByteArrayDataInput in = ByteStreams.newDataInput(data);
		InventoryInterfaceState state = new InventoryInterfaceState();
		state.showHolo = in.readBoolean();
		state.pos = new Vector3(in.readInt(),in.readInt(),in.readInt());
		boolean not = in.readBoolean();
		int numItems = in.readInt();
		if (numItems >= 0)
		{
			ItemFilter filter = new ItemFilter();
			filter.not = not;
			for (int i = 0; i<numItems; i++)
			{
				try {
					filter.stacks.add(Packet.readItemStack(in));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			state.filter = filter;
		}
		return state;
	}

}
