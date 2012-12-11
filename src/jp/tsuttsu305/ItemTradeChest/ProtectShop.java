package jp.tsuttsu305.ItemTradeChest;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ProtectShop implements Listener {
	private ItemTradeChest itc = null;

	public ProtectShop(ItemTradeChest itc) {
		this.itc = itc;
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onExplode(EntityExplodeEvent event){
		for (Block block : event.blockList()){
			if (block.getType().equals(Material.WALL_SIGN)){
				String[] lines = ((Sign)block.getState()).getLines();
				if (lines[0].equalsIgnoreCase("[shop]")){
					if (lines[2].matches("(out:|in:)[A-Z]+:[0-9]+") || lines[2].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
						if (lines[3].matches("(out:|in:)[A-Z]+:[0-9]+") || lines[3].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
							event.setCancelled(true);
							break;
						}
					}
				}
			}
			Block[] blockArea = {
					block.getRelative(BlockFace.NORTH),
					block.getRelative(BlockFace.EAST),
					block.getRelative(BlockFace.SOUTH),
					block.getRelative(BlockFace.WEST)};
			for (int i =0; i <= 3;i++){
				if (blockArea[i].getType().equals(Material.WALL_SIGN)){
					String[] lines = ((Sign)blockArea[i].getState()).getLines();
					if (lines[0].equalsIgnoreCase("[shop]")){
						if (lines[2].matches("(out:|in:)[A-Z]+:[0-9]+") || lines[2].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
							if (lines[3].matches("(out:|in:)[A-Z]+:[0-9]+") || lines[3].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
								event.setCancelled(true);
								break;
							}
						}
					}
				}
			}
		}
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerBreak(BlockBreakEvent event){
		Block block = event.getBlock();
		if (block.getType().equals(Material.WALL_SIGN)){
			String[] lines = ((Sign)block.getState()).getLines();
			if (lines[0].equalsIgnoreCase("[shop]")){
				if (lines[2].matches("(out:|in:)[A-Z]+:[0-9]+") || lines[2].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
					if (lines[3].matches("(out:|in:)[A-Z]+:[0-9]+") || lines[3].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
						if (!(event.getPlayer().equals(itc.getServer().getPlayer(lines[1])))){
							event.setCancelled(true);
						}
					}
				}
			}
		}

		Block[] blockArea = {
				block.getRelative(BlockFace.NORTH),
				block.getRelative(BlockFace.EAST),
				block.getRelative(BlockFace.SOUTH),
				block.getRelative(BlockFace.WEST)};
		for (int i =0; i <= 3;i++){
			if (blockArea[i].getType().equals(Material.WALL_SIGN)){
				String[] lines = ((Sign)blockArea[i].getState()).getLines();
				if (lines[0].equalsIgnoreCase("[shop]")){
					if (lines[2].matches("(out:|in:)[A-Z]+:[0-9]+") || lines[2].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
						if (lines[3].matches("(out:|in:)[A-Z]+:[0-9]+") || lines[3].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
							if (!(event.getPlayer().equals(itc.getServer().getPlayer(lines[1])))){
								event.setCancelled(true);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPistonEx(BlockPistonExtendEvent event){
		for (Block block : event.getBlocks()){
			Block[] blockArea = {
					block.getRelative(BlockFace.NORTH),
					block.getRelative(BlockFace.EAST),
					block.getRelative(BlockFace.SOUTH),
					block.getRelative(BlockFace.WEST)};
			for (int i =0; i <= 3;i++){
				if (blockArea[i].getType().equals(Material.WALL_SIGN)){
					String[] lines = ((Sign)blockArea[i].getState()).getLines();
					if (lines[0].equalsIgnoreCase("[shop]")){
						if (lines[2].matches("(out:|in:)[A-Z]+:[0-9]+") || lines[2].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
							if (lines[3].matches("(out:|in:)[A-Z]+:[0-9]+") || lines[3].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
								event.setCancelled(true);
								break;
							}
						}
					}
				}
			}
		}
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPistonEx(BlockPistonRetractEvent event){
		Block block = event.getRetractLocation().getBlock();
			Block[] blockArea = {
					block.getRelative(BlockFace.NORTH),
					block.getRelative(BlockFace.EAST),
					block.getRelative(BlockFace.SOUTH),
					block.getRelative(BlockFace.WEST)};
			for (int i =0; i <= 3;i++){
				if (blockArea[i].getType().equals(Material.WALL_SIGN)){
					String[] lines = ((Sign)blockArea[i].getState()).getLines();
					if (lines[0].equalsIgnoreCase("[shop]")){
						if (lines[2].matches("(out:|in:)[A-Z]+:[0-9]+") || lines[2].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
							if (lines[3].matches("(out:|in:)[A-Z]+:[0-9]+") || lines[3].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
								event.setCancelled(true);
								break;
							}
						}
					}
				}
			}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPistonEx(BlockBurnEvent event){
		Block block = event.getBlock();
			Block[] blockArea = {
					block.getRelative(BlockFace.NORTH),
					block.getRelative(BlockFace.EAST),
					block.getRelative(BlockFace.SOUTH),
					block.getRelative(BlockFace.WEST)};
			for (int i =0; i <= 3;i++){
				if (blockArea[i].getType().equals(Material.WALL_SIGN)){
					String[] lines = ((Sign)blockArea[i].getState()).getLines();
					if (lines[0].equalsIgnoreCase("[shop]")){
						if (lines[2].matches("(out:|in:)[A-Z]+:[0-9]+") || lines[2].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
							if (lines[3].matches("(out:|in:)[A-Z]+:[0-9]+") || lines[3].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
								event.setCancelled(true);
								break;
							}
						}
					}
				}
			}
	}
}
