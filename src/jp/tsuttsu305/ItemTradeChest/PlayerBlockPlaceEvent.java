package jp.tsuttsu305.ItemTradeChest;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerBlockPlaceEvent implements Listener {
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerBlockPlace(BlockPlaceEvent event){
		Block block = event.getBlockAgainst();
		if (block.getType().equals(Material.WALL_SIGN)){
			if (block.getRelative(BlockFace.DOWN).getType().equals(Material.CHEST)){
				String[] lines = ((Sign)block.getState()).getLines();
				if (lines[0].equalsIgnoreCase("[shop]")){
					event.setCancelled(true);
				}
			}
		}
		return;
	}
}
