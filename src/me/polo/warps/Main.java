package me.polo.warps;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    public static Main plugin;
    Map<Integer, ItemStack[]> items = new HashMap<Integer, ItemStack[]>();

    public void onEnable() {

        plugin = this;
        //warps
        getServer().getPluginCommand("warp").setExecutor(new Warps());
        getServer().getPluginCommand("delwarp").setExecutor(new Warps());
        getServer().getPluginCommand("setwarp").setExecutor(new Warps());

        getServer().getPluginCommand("warpgui").setExecutor(new WarpGUI());
        getServer().getPluginManager().registerEvents(new WarpGUI(), this);

        WarpGUI.initialize();

        loadConfig();
    }

    public void onDisable() {
    }

    public void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
