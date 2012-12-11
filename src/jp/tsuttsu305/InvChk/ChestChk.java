package jp.tsuttsu305.InvChk;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

public class ChestChk {

	//チェスト　指定したアイテムが何個あるか
	public static int countChestItem(Block chest, ItemStack item){
		if (item == null || chest == null)return 0;
		int item_amu = 0;
		//Material取得 //耐久値
		Material ma = item.getType();
		short du = item.getDurability();
		//Chest中身すべて格納
		ItemStack[] chestItems = ((Chest)chest.getState()).getBlockInventory().getContents();

		//中身の個数
		for (int i = 0;i <chestItems.length;i++){
			if (chestItems[i] == null)continue;
			if (chestItems[i].getType().equals(ma)){
				if (chestItems[i].getDurability() == du){
					item_amu = item_amu +  chestItems[i].getAmount();
				}
			}
		}
		return item_amu;
	}

	//指定したアイテムが追加可能かをチェックする
	public static boolean chestCanAdd(Block chest, ItemStack item){
		if (item == null || chest == null) return false;

		int maxStack = item.getMaxStackSize();
		Material ma = item.getType();
		short du = item.getDurability();
		//Chestの中身格納
		ItemStack[] chestItems = ((Chest)chest.getState()).getBlockInventory().getContents();
		int canAdd = 0;
		//確認開始
		for (int i = 0;i < chestItems.length;i++){
			if (chestItems[i] == null) {
				canAdd = canAdd + 64;
				continue;
			}

			if (chestItems[i].getType().equals(ma)){
				if (chestItems[i].getDurability() == du){
					if (chestItems[i].getAmount() == maxStack){
						continue;
					}else{
						canAdd = chestItems[i].getMaxStackSize() - chestItems[i].getAmount();
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
