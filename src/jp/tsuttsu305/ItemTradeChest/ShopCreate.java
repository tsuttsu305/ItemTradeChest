package jp.tsuttsu305.ItemTradeChest;

import jp.tsuttsu305.Lockette.CheckLockette;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;


public class ShopCreate implements Listener {
	private ItemTradeChest itemTradeChest = null;

	public ShopCreate(ItemTradeChest itemTradeChest) {
		this.itemTradeChest =  itemTradeChest;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onSignChange(SignChangeEvent event){
		//使う変数共
		//Sign sign = (Sign) event.getBlock().getState();
		String[] signLines = event.getLines();
		Block center = event.getBlock();
		Block chest = center.getRelative(BlockFace.DOWN);
		Player player = event.getPlayer();

		//Eventがキャンセルされていたら処理を行わない
		if (event.isCancelled() == true) return;
		//WALL_SIGN以外は処理を行わない
		if (!event.getBlock().getType().equals(Material.WALL_SIGN)) return;
		//看板の下がChest以外の場合は処理を行わない
		if (!chest.getType().equals(Material.CHEST)) return;
		//1行めが[shop]以外は無視
		if (!signLines[0].equalsIgnoreCase(itemTradeChest.getLine1())) return;

		//Locketteロック判定 - 所有者ではない場合は1行目にエラーを返す
		if (ItemTradeChest.Lockette){
			if (CheckLockette.isCheckBlock(chest, player) == false){
				event.setLine(0, ChatColor.RED + "" +  ChatColor.ITALIC + "--Error!--");
				player.sendMessage(ChatColor.RED + "[Shop] " + itemTradeChest.getMsg("notPlayerChest"));
				return;
			}
		}

		//内容整形
		//2行目　販売者のID表示。無条件書き換え
		event.setLine(1, player.getName());

		//3行目 out:Material:数量。　書き込みはid:個数
		//4行目 in:Material:数量 書き込みはid:数量
		for (int i = 2; i <= 3; i++){
			event.setLine(i, chkSign(event.getLines(), i, i-2, player));
		}
	}

	/*---------------------------------------------------------------------------------------------------------*/

	//看板の3,4行目判定用　返り値はError判定用
	//看板の内容, 行番号, OUTかINか, Player
	@SuppressWarnings("unused")
	private String chkSign(String[] lines, int lineN, int io, Player pl) throws NumberFormatException{
		String ios = "";
		if (io == 0){
			ios = "out";
		}else{
			ios = "in";
		}
		if (lines[lineN].matches("[0-9]+:[0-9]+") || lines[lineN].matches("[0-9]+:[0-9]+:[0-9]+")){
			String[] line = lines[lineN].split(":");
			String outName = "Error";
			try {
				outName = Material.getMaterial(Integer.parseInt(line[0])).toString();
			} catch (Exception e) {
				pl.sendMessage(ChatColor.RED + "[Shop] " + itemTradeChest.getMsg("notFoundID"));
				outName = "Error";
			}
			if (lines[lineN].matches("[0-9]+:[0-9]+")){
				try {
					int check = Integer.parseInt(line[1]);
				} catch (NumberFormatException e) {
					// TODO 自動生成された catch ブロック
					pl.sendMessage(ChatColor.RED + "[Shop] 値が異常です!");
					return ChatColor.RED + "--Error--";
				}
				lines[lineN] = ios + ":" + outName + ":" + line[1];
			}else if (lines[lineN].matches("[0-9]+:[0-9]+:[0-9]+")){
				try {
					int check = Integer.parseInt(line[2]);
				} catch (NumberFormatException e) {
					// TODO 自動生成された catch ブロック
					pl.sendMessage(ChatColor.RED + "[Shop] 値が異常です!");
					return ChatColor.RED + "--Error--";
				}
				lines[lineN]	 = ios + ":" + outName + ":" + line[1] + ":" + line[2];
			}else{
				lines[lineN] = ChatColor.RED + "" +  ChatColor.ITALIC + "--Error!--";
			}
		}else if(lines[lineN].equalsIgnoreCase("none")){
			lines[lineN] =  ios + ":NONE:0";
		}else {
			lines[lineN] =  ChatColor.RED + "" +  ChatColor.ITALIC + "--Error!--";
		}
		return lines[lineN];
	}
}
