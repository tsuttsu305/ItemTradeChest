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
	
	//Playerがアイテムを追加できるか
	public static boolean playerCanAdd(Player pl, ItemStack item){
		if (pl == null || item == null)return false;
		int maxStack = item.getMaxStackSize();
		Material ma = item.getType();
		short du = item.getDurability();
		
		//PlayerのInventoryの中身取得
		ItemStack[] playerInv = pl.getInventory().getContents();
		
		int canAdd = 0;
		//確認開始
		for (int i = 0;i < playerInv.length;i++){
			if (playerInv[i] == null) {
				canAdd = canAdd + 64;
				continue;
			}
			
			if (playerInv[i].getType().equals(ma)){
				if (playerInv[i].getDurability() == du){
					if (playerInv[i].getAmount() == maxStack){
						continue;
					}else{
						canAdd = playerInv[i].getMaxStackSize() - playerInv[i].getAmount();
					}
				}
			}
		}
		
		if (canAdd >= item.getAmount()){
			return true;
		}
		return false;
	}
}
