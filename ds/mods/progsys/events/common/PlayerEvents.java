package ds.mods.progsys.events.common;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import ds.mods.progsys.blocks.BlockInventoryInterface;
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
				if (stack == null)
				{
					tile.showHolo = !tile.showHolo;
				}
				else if (stack == null && player.isSneaking())
				{
					System.out.println("Not dead code :P");
				}
				event.setCanceled(true);
			}
		}
	}
}
