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
		if (itc.getProtection("explosion") == false) return;
		for (Block block : event.blockList()){
			if (isProtectSign(block)){
				event.setCancelled(true);
				return;
			}
			Block[] blockArea = getBlockArea(block);
			for (int i =0; i <= 3;i++){
				if (isProtectSign(blockArea[i])){
					event.setCancelled(true);
					break;
				}
			}
		}
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerBreak(BlockBreakEvent event){
		if (event.getBlock().getType().equals(Material.WALL_SIGN)){
			if (itc.getProtection("player") == false) return;
			Block block = event.getBlock();
			if (isProtectSign(block)){
				if (!(event.getPlayer().equals(itc.getServer().getPlayer(((Sign)block.getState()).getLine(1))))){
					event.setCancelled(true);
					return;
				}
			}
		}else{
			//((Sign)block.getState()).getLine(1)
			Block[] blockArea = getBlockArea(event.getBlock());
			for (int i =0; i <= 3;i++){
				if (isProtectSign(blockArea[i])){
					if (!(event.getPlayer() == itc.getServer().getPlayerExact(((Sign)blockArea[i].getState()).getLine(1)))){
						event.setCancelled(true);
						break;
					}
				}
			}
		}
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPistonEx(BlockPistonExtendEvent event){
		if (itc.getProtection("piston") == false) return;
		for (Block block : event.getBlocks()){
			Block[] blockArea = getBlockArea(block);
			for (int i =0; i <= 3;i++){
				if (isProtectSign(blockArea[i])){
					event.setCancelled(true);
					break;
				}
			}
		}
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPistonEx(BlockPistonRetractEvent event){
		if (itc.getProtection("piston") == false) return;
		Block block = event.getRetractLocation().getBlock();
		Block[] blockArea = getBlockArea(block);
		for (int i =0; i <= 3;i++){
			if (isProtectSign(blockArea[i])){
				event.setCancelled(true);
				break;
			}
		}
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onBlockBurn(BlockBurnEvent event){
		if (itc.getProtection("burn") == false) return;
		Block block = event.getBlock();
		Block[] blockArea = getBlockArea(block);
		for (int i =0; i <= 3;i++){
			if (isProtectSign(blockArea[i])){
				event.setCancelled(true);
				break;
			}
		}
	}

	public boolean isProtectSign(Block block){
		if (block.getType().equals(Material.WALL_SIGN)){
			String[] lines = ((Sign)block.getState()).getLines();
			if (lines[0].equalsIgnoreCase("[shop]")){
				if (lines[2].matches("(out:|in:)[A-Z_]+:[0-9]+") || lines[2].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
					if (lines[3].matches("(out:|in:)[A-Z_]+:[0-9]+") || lines[3].matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
						return true;
					}
				}
			}
		}
		return false;
	}

	public Block[] getBlockArea(Block block){
		Block[] blockA = {
				block.getRelative(BlockFace.NORTH),
				block.getRelative(BlockFace.EAST),
				block.getRelative(BlockFace.SOUTH),
				block.getRelative(BlockFace.WEST)};
		return blockA;
	}
}
