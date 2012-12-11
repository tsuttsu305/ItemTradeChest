package jp.tsuttsu305.ItemTradeChest;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class ItemTradeChest extends JavaPlugin {
	public static ItemTradeChest plugin;
	Logger logger = Logger.getLogger("Minecraft");
	public static boolean Lockette = false;
	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(new ShopCreate(), this);
		getServer().getPluginManager().registerEvents(new PlayerUseShop(this), this);
		getServer().getPluginManager().registerEvents(new PlayerBlockPlaceEvent(), this);
		getServer().getPluginManager().registerEvents(new ProtectShop(this), this);
		if (getServer().getPluginManager().isPluginEnabled("Lockette")){
			Lockette = true;
			logger.info("[ItemTradeChest] Hooked Lockette");
		}
	}

}
