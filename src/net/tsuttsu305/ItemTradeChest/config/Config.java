package net.tsuttsu305.ItemTradeChest.config;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import net.tsuttsu305.ItemTradeChest.ItemTradeChest;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class Config {
	private ItemTradeChest plugin;

	//config関係
	private FileConfiguration conf;
	private File confDir;

	//使用するConfigの名前
	private  String confName;

	/**
	 * 
	 * @param configName ロードするConfigの名前
	 * @param plugins JavaPlugin
	 */
	public Config(String configName, ItemTradeChest plugins) {
		this.plugin = plugins;
		this.confName = configName;
		//configのパスを取得
		this.confDir = plugin.getDataFolder();
	}

	public void loadConfig(){
		//Config.ymlへのパスを取得
		File confPath = new File(confDir, confName);

		//configが存在しなかった場合はjarからコピー
		if (!(confPath.exists())){
			plugin.saveResource(confName, false);
		}
		//configを格納
		conf = YamlConfiguration.loadConfiguration(confPath);
	}

	public FileConfiguration getConfig(){
		if (conf == null){
			loadConfig();
		}
		return conf;
	}

	public void saveConfig(){
		if (conf == null || confDir == null){
			return;
		}else{
			try {
				getConfig().save(confDir + "/");
			} catch (IOException e) {
				plugin.getLogger().log(Level.WARNING, "Cannot save " + confName + " " + e);
			}
		}
	}
}