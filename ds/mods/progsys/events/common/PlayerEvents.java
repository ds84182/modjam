package ds.mods.progsys.events.common;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import ds.mods.progsys.blocks.BlockInventoryInterface;
import ds.mods.progsys.items.ItemWrench;
import ds.mods.progsys.net.InventoryInterfaceState;
import ds.mods.progsys.net.PacDispat;
import ds.mods.progsys.tile.TileEntityInventoryInterface;

public class PlayerEvents {
	
	@ForgeSubscribe
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.action == Action.RIGHT_CLICK_BLOCK)
		{
			Block b = Block.blocksList[event.entityPlayer.worldObj.getBlockId(event.x, event.y, event.z)];
			if (b instanceof BlockInventoryInterface)
			{
				EntityPlayer player = event.entityPlayer;
				ItemStack stack = player.getHeldItem();
				TileEntityInventoryInterface tile = (TileEntityInventoryInterface) player.worldObj.getBlockTileEntity(event.x, event.y, event.z);
				/*
				 * Hand empty: Toggle holo
				 * Shift + Hand Empty: Take last block out of filter
				 * Hand full: Add to filter
				 * Hand has wrench: Go through sides
				 * Shift + Hand has wrench: Toggle NOT mode
				 * Jump + Shift + Hand has wrench: Put wrench into filter
				 */
				if (stack == null && player.isSneaking())
				{
					
				}
				else if (stack == null)
				{
					if (event.entityPlayer.worldObj.isRemote)
					{
						tile.showHolo = !tile.showHolo;
						PacDispat.sendPacketToServer(new InventoryInterfaceState(tile));
					}
				}
				else if (stack.getItem() instanceof ItemWrench)
				{
					if (!event.entityPlayer.worldObj.isRemote)
					{
						player.worldObj.setBlockMetadataWithNotify(event.x, event.y, event.z, (player.worldObj.getBlockMetadata(event.x, event.y, event.z)+1)%6, 3);
						player.swingItem();
						event.setCanceled(true);
					}
				}
			}
		}
	}
}
