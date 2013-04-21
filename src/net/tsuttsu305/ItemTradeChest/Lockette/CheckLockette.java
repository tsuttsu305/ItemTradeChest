package net.tsuttsu305.ItemTradeChest.Lockette;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class CheckLockette {
	
	public static boolean isCheckBlock(Block chest, Player pl){
		if (!org.yi.acru.bukkit.Lockette.Lockette.isProtected(chest)) return true;
		
		if (org.yi.acru.bukkit.Lockette.Lockette.isOwner(chest, pl.getName()))return true;

		
		return false;
	}

}
