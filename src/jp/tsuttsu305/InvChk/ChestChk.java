package jp.tsuttsu305.InvChk;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

public class ChestChk {
	
	//チェスト　指定したアイテムが何個あるか
	public static int countChestItem(Block chest, ItemStack item){
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
}
