package jp.tsuttsu305.ItemTradeChest;

import java.util.logging.Level;
import java.util.logging.Logger;

import jp.tsuttsu305.config.Config;

import org.bukkit.plugin.java.JavaPlugin;

public class ItemTradeChest extends JavaPlugin {
	public static ItemTradeChest plugin;
	Logger logger = Logger.getLogger("Minecraft");
	public static boolean Lockette = false;
	private Config conf, msg;
	
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
		
		conf = new Config("config.yml", this);
		conf.loadConfig();
		msg = new Config(conf.getConfig().getString("language") + ".yml", this);
		msg.loadConfig();
		logger.log(Level.INFO, "[ItemTradeChest] Load language: " + conf.getConfig().getString("language"));
		logger.log(Level.INFO,"[ItemTradeChest] " +  msg.getConfig().getString("langChk"));
	}

}
