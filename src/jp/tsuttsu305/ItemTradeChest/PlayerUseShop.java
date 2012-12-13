package jp.tsuttsu305.ItemTradeChest;

import jp.tsuttsu305.InvChk.ChestChk;
import jp.tsuttsu305.InvChk.PlayerInvChk;
import jp.tsuttsu305.Lockette.CheckLockette;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
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

	@SuppressWarnings("deprecation")
	@EventHandler
	public void signClick(PlayerInteractEvent event){
		if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK))return;
		Block block;
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR)){
			block = event.getPlayer().getTargetBlock(null, 1);
		}else{
			block = event.getClickedBlock();
		}
		
		//クリックしたのが壁の看板以外なら終了
		if (block.getType() != Material.WALL_SIGN){
			return;
		}
		
		//看板の内容格納
		String[] signLines = ((Sign)block.getState()).getLines();		//看板の内容
		
		//キャンセルされている場合は終了
		if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR))){
		if (event.isCancelled())return;
		}
		
		Block chest = block.getRelative(BlockFace.DOWN);
		
		//看板の下がChest以外の場合は処理を行わない
		if (!chest.getType().equals(Material.CHEST)) return;
		
		//1行めが[shop]以外は無視
		if (!(signLines[0].equalsIgnoreCase(ttc.getLine1()))) return;
		Player player = event.getPlayer();
		//Shop作成者が本当にチェスト持ち主かを再確認
		if (ItemTradeChest.Lockette){
			if (CheckLockette.isCheckBlock(chest, ttc.getServer().getPlayerExact(signLines[1])) == false){
				String error1 = ttc.getMsg("error1");
				error1 = error1.replaceAll("&L", player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ());
				player.sendMessage(ChatColor.RED + "[Shop] " + error1);
				event.setCancelled(true);
				return;
			}
		}

		//セクションサインが混ざるので、不要な表記記号を削除
		signLines = removeColor(signLines);
		//看板にエラーがないか確認
		for (int i = 0;i <= 3; i++){
			if (signLines[i].toLowerCase().matches(".*(error).*")){
				String error2 = ttc.getMsg("error2");
				error2 = error2.replaceAll("&L", player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ());
				player.sendMessage(ChatColor.RED + "[Shop] " + error2);
				event.setCancelled(true);
				return;
			}
		}

		//等価交換処理開始
		event.setUseItemInHand(Result.DENY);
		ItemStack outItem = getItemStack(signLines[2]);
		ItemStack inItem = getItemStack(signLines[3]);
		//if (outItem == null || inItem == null)return;
		//チェストの中身チェック
		if (outItem != null){
			if (ChestChk.countChestItem(chest, outItem) < outItem.getAmount()){
				player.sendMessage(ChatColor.AQUA + "[Shop] " + ttc.getMsg("noStack"));
				return;
			}
		}
		//PlayerのInventoryチェック
		if (inItem != null){
			if (PlayerInvChk.countPlayerInvItem(player, inItem) < inItem.getAmount()){
				player.sendMessage(ChatColor.AQUA + "[Shop] " + ttc.getMsg("notHave"));
				return;
			}
		}

		//チェストに格納が可能かCheck
		if (inItem != null){
			if (!(ChestChk.chestCanAdd(chest, inItem))){
				player.sendMessage(ChatColor.AQUA + "[Shop] " + ttc.getMsg("chestFull"));
				if (ttc.getServer().getPlayerExact(signLines[1]).isOnline()){
					String chestFullToCreater = ttc.getMsg("chestFullToCreater");
					chestFullToCreater = chestFullToCreater.replaceAll("&L", player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ());
					ttc.getServer().getPlayerExact(signLines[1]).sendMessage(ChatColor.AQUA + "[Shop] " + chestFullToCreater);
					}
				return;
			}
		}

		//Playerが格納可能かを確認
		if (outItem != null){
			if (!(PlayerInvChk.playerCanAdd(player, outItem))){
				player.sendMessage(ChatColor.AQUA + "[Shop] " + ttc.getMsg("playerFull"));
				return;
			}
		}

		//交換処理実行
		//チェスト追加
		if (inItem != null){
			((Chest)chest.getState()).getBlockInventory().addItem(inItem);
			player.getInventory().removeItem(inItem);
		}
		//Player
		if (outItem != null){
			player.getInventory().addItem(outItem);
			((Chest)chest.getState()).getBlockInventory().removeItem(outItem);
		}
		player.updateInventory();
		player.sendMessage(ChatColor.GREEN + "[Shop] " + ttc.getMsg("success"));
		if (outItem != null && inItem != null) {
			if (ttc.getServer().getPlayerExact(signLines[1]).isOnline()) {
				String successToCreater = ttc.getMsg("successToCreater");
				successToCreater = successToCreater.replaceAll("&P", player.getName());
				successToCreater = successToCreater.replaceAll("&I", inItem.getType().toString());
				successToCreater = successToCreater.replaceAll("&IA", inItem.getAmount() + "");
				successToCreater = successToCreater.replaceAll("&O", outItem.getType().toString());
				successToCreater = successToCreater.replaceAll("&AO", outItem.getAmount() + "");
				ttc.getServer()
						.getPlayerExact(signLines[1])
						.sendMessage(
								ChatColor.GREEN + "[Shop] " + successToCreater);
			}
			String successToServer = ttc.getMsg("successToServer");
			successToServer = successToServer.replaceAll("&P", player.getName());
			successToServer = successToServer.replaceAll("&I", inItem.getType().toString());
			successToServer = successToServer.replaceAll("&IA", inItem.getAmount() + "");
			successToServer = successToServer.replaceAll("&O", outItem.getType().toString());
			successToServer = successToServer.replaceAll("&AO", outItem.getAmount() + "");
			ttc.logger.info("[Shop] " + successToServer);
		}else{
			String successToCreater = ttc.getMsg("successToCreater");
			String successToServer = ttc.getMsg("successToServer");
			
			successToCreater = successToCreater.replaceAll("&P", player.getName());
			successToServer = successToServer.replaceAll("&P", player.getName());
			if (inItem == null){
				successToCreater = successToCreater.replaceAll("&I", "None");
				successToServer = successToServer.replaceAll("&I", "None");
				successToCreater = successToCreater.replaceAll("&IA", "0");
				successToServer = successToServer.replaceAll("&IA", "0");
			}else{
				successToCreater = successToCreater.replaceAll("&I", inItem.getType().toString());
				successToCreater = successToCreater.replaceAll("&IA", inItem.getAmount() + "");
				successToServer = successToServer.replaceAll("&I", inItem.getType().toString());
				successToServer = successToServer.replaceAll("&IA", inItem.getAmount() + "");
			}
			
			if (outItem == null){
				successToCreater = successToCreater.replaceAll("&O", "None");
				successToServer = successToServer.replaceAll("&O", "None");
				successToCreater = successToCreater.replaceAll("&OA", "0");
				successToServer = successToServer.replaceAll("&OA", "0");
			}else{
				successToCreater = successToCreater.replaceAll("&O", inItem.getType().toString());
				successToCreater = successToCreater.replaceAll("&OA", inItem.getAmount() + "");
				successToServer = successToServer.replaceAll("&O", inItem.getType().toString());
				successToServer = successToServer.replaceAll("&OA", inItem.getAmount() + "");
			}
			if (ttc.getServer().getPlayerExact(signLines[1]).isOnline()) {
				ttc.getServer().getPlayerExact(signLines[1]).sendMessage(ChatColor.GREEN + "[Shop] " + successToCreater);
			}
			ttc.logger.info("[Shop] " + successToServer);
		}
		return;
	}


	//in outのとこからItemStackを取得する
	public ItemStack getItemStack(String str){
		if (str.matches("(out:|in:)[A-Z]+:[0-9]+:[0-9]+")){
			String[] items = str.split(":");		// 0out : 1Material : 2拡張ID : 3個数
			if (items[1].equalsIgnoreCase("none")){
				return null;
			}
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
			if (items[1].equalsIgnoreCase("none")){
				return null;
			}
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
