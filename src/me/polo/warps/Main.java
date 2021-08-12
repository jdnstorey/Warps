package me.polo.warps;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public FileConfiguration config = this.getConfig();
    public static Main plugin;

    public void onEnable() {
        plugin = this;
        //warps
        getServer().getPluginCommand("warp").setExecutor(new Warps());
        getServer().getPluginCommand("delwarp").setExecutor(new Warps());
        getServer().getPluginCommand("setwarp").setExecutor(new Warps());

        WarpGUI.initialize();

        loadConfig();
    }

    public void onDisable() {}

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
