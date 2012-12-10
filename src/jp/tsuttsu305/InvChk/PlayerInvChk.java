package jp.tsuttsu305.InvChk;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerInvChk {

	public static int countPlayerInvItem(Player pl, ItemStack it){
		int invAu = 0;
		//Material取得
		Material ma = it.getType();
		//耐久値取得
		short du = it.getDurability();
		//PlayerのInventoryの中身を全て取得
		ItemStack[] playerInv = pl.getInventory().getContents();
		
		//カウント
		for (int i = 0;i <playerInv.length;i++){
			if (playerInv[i] == null)continue;
			if (playerInv[i].getType().equals(ma)){
				if (playerInv[i].getDurability() == du){
					invAu = invAu +  playerInv[i].getAmount();
				}
			}
		}
		return invAu;
	}
}
