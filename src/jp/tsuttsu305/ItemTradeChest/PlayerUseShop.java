package jp.tsuttsu305.ItemTradeChest;

import jp.tsuttsu305.Lockette.CheckLockette;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerUseShop implements Listener {
	private ItemTradeChest ttc = null;
	
	public PlayerUseShop(ItemTradeChest ttc) {
		this.ttc = ttc;
	}

	@EventHandler
	public void signClick(PlayerInteractEvent event){
		//クリックしたのが壁の看板以外なら終了
		if (!(event.getClickedBlock().getType() == Material.WALL_SIGN)){
			return;
		}
		//看板の内容格納
		String[] signLines = ((Sign)event.getClickedBlock().getState()).getLines();		//看板の内容

		//キャンセルされている場合は終了
		if (event.isCancelled()) return;
		Block chest = event.getClickedBlock().getRelative(BlockFace.DOWN);
		//看板の下がChest以外の場合は処理を行わない
		if (!chest.getType().equals(Material.CHEST)) return;

		//1行めが[shop]以外は無視
		if (!signLines[0].equalsIgnoreCase("[shop]")) return;
		Player player = event.getPlayer();
		//Shop作成者が本当にチェスト持ち主かを再確認
		if (ItemTradeChest.Lockette){
			if (CheckLockette.isCheckBlock(chest, ttc.getServer().getPlayer(signLines[1])) == false){
				player.sendMessage("[Shop]" + ChatColor.RED + "Please report the location to the administrator! Code: 001");
				event.setCancelled(true);
				return;
			}
		}
		
		//セクションサインが混ざるので、不要な表記記号を削除
		signLines = removeColor(signLines);
		//看板にエラーがないか確認
		for (int i = 0;i <= 3; i++){
			if (signLines[i].toLowerCase().matches(".*(error).*")){
				player.sendMessage("[Shop]" + ChatColor.RED + "Please report the location to the administrator! Code: 002");
				event.setCancelled(true);
				return;
			}
		}
		
		//等価交換処理開始//右クリックで処理を行う
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			event.setUseItemInHand(Result.DENY);
			ItemStack outItem = getItemStack(signLines[2]);
			ItemStack inItem = getItemStack(signLines[3]);
			if (outItem == null || inItem == null)return;
			//チェストの中身を配列に格納
			ItemStack[] chestItem = ((Chest)chest.getState()).getBlockInventory().getContents();
			int chestMax = ((Chest)chest.getState()).getBlockInventory().getSize();
			
		}
	}
	
	//in outのとこからItemStackを取得する
	public ItemStack getItemStack(String str){
		if (str.matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
			String[] items = str.split(":");		// 0out : 1Material : 2拡張ID : 3個数
			Material mat = Material.getMaterial(items[1]);	//Material
			short dam = Short.parseShort(items[2]);				//ダメージ値
			int count = Integer.parseInt(items[3]);				//数量
			
			//ItemStack作るよー
			ItemStack reItem = new ItemStack(mat);
			reItem.setDurability(dam);
			reItem.setAmount(count);
			
			return reItem;
		}else if (str.matches("(out:|in:)[A-Z]+:[0-9]+")){
			String[] items = str.split(":");		// 0out : 1Material :  2個数
			Material mat = Material.getMaterial(items[1]);	//Material
			int count = Integer.parseInt(items[2]);				//数量
			
			//ItemStack作るよー
			ItemStack reItem = new ItemStack(mat);
			reItem.setAmount(count);
			
			return reItem;
		}
		return null;
	}
	
	//看板に含まれる拡張表記除去用
	public String[] removeColor(String[] lines){
		for (int i = 0;i <= 3;i++){
			lines[i] = lines[i].replaceAll("(§([a-z0-9]))", "");
		}
		return lines;
	}
}
