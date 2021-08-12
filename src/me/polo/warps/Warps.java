package me.polo.warps;

import me.polo.warps.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Warps implements CommandExecutor {

    public Main plugin = Main.getPlugin(Main.class);

    public String name;
    public ArrayList<UUID> cooldown = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("setwarp")) {
                if (p.hasPermission("admincore.setwarp")) {
                    if (args.length == 0) {
                        p.sendMessage(ChatColor.RED + "Please specify a name");
                    } else if(args.length == 1){
                        Location loc = p.getLocation();

                        name = args[0].toLowerCase();

                        plugin.getConfig().set("Warps." + name + ".X", loc.getX());
                        plugin.getConfig().set("Warps." + name + ".Y", loc.getY());
                        plugin.getConfig().set("Warps." + name + ".Z", loc.getZ());
                        plugin.getConfig().set("Warps." + name + ".World", loc.getWorld().getName());
                        plugin.getConfig().set("Warps." + name + ".Pitch", loc.getPitch());
                        plugin.getConfig().set("Warps." + name + ".Yaw", loc.getYaw());
                        plugin.getConfig().set("Warps." + name + ".Item", p.getInventory().getItemInMainHand().getType());
                        plugin.saveConfig();
                        p.sendMessage(ChatColor.GREEN + "Warp " + name + " created successfully");
                    } else {
                        p.sendMessage(ChatColor.RED + "Insufficient Arguments");
                    }
                } else {
                    p.sendMessage(ChatColor.RED+ "Insufficient Permission");
                }
            }

            if(cmd.getName().equalsIgnoreCase("delwarp")){
                if (args.length == 0) {
                    p.sendMessage(ChatColor.RED + "Please specify a name");
                } else if(args.length == 1) {
                    name = args[0].toLowerCase();
                    if (plugin.getConfig().getConfigurationSection("Warps." + name) == null) {
                        p.sendMessage(ChatColor.RED + "Warp " + name + " doesn't exist");
                    } else {
                        plugin.getConfig().set("Warps." + name, null);
                        plugin.saveConfig();
                        p.sendMessage(ChatColor.RED + "Warp " + name + " deleted successfully");
                    }
                }
            }

            if(cmd.getName().equalsIgnoreCase("warp")){
                if (args.length == 0) {
                    p.sendMessage(ChatColor.RED + "Please specify a name");
                    p.sendMessage(" ");
                    p.sendMessage(ChatColor.BLUE + "Possible warps are:");
                    p.sendMessage(ChatColor.WHITE + "" + plugin.getConfig().getConfigurationSection("Warps").getKeys(false));

                } else if(args.length == 1) {
                    name = args[0].toLowerCase();

                    long days = (long) 1 / (24 * 8);
                    long time = 1728000 /* 1day */ * days;
                    if(cooldown.contains(p.getUniqueId())){

                    } else {
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> cooldown.remove(p.getUniqueId()), time);
                        if (plugin.getConfig().getConfigurationSection("Warps." + name) == null) {
                            p.sendMessage(ChatColor.RED + "Warp " + name + " doesn't exist");
                        } else {
                            // saving player loc
                            Location playerloc = p.getLocation();
                            plugin.getConfig().set(p.getName() + ".X", playerloc.getX());
                            plugin.getConfig().set(p.getName() + ".Y", playerloc.getY());
                            plugin.getConfig().set(p.getName() + ".Z", playerloc.getZ());
                            plugin.getConfig().set(p.getName() + ".World", playerloc.getWorld().getName());
                            plugin.getConfig().set(p.getName() + ".Pitch", playerloc.getPitch());
                            plugin.getConfig().set(p.getName() + ".Yaw", playerloc.getYaw());
                            plugin.saveConfig();

                            // teleporting
                            World world = Bukkit.getServer().getWorld(plugin.getConfig().getString("Warps." + name + ".World"));
                            double x = plugin.getConfig().getDouble("Warps." + name + ".X");
                            double y = plugin.getConfig().getDouble("Warps." + name + ".Y");
                            double z = plugin.getConfig().getDouble("Warps." + name + ".Z");
                            float yaw = plugin.getConfig().getInt("Warps." + name + ".Yaw");
                            float pitch = plugin.getConfig().getInt("Warps." + name + ".Pitch");

                            Location loc = new Location(world, x,y, z, yaw, pitch);
                            p.teleport(loc);

                            cooldown.add(p.getUniqueId());
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Insufficient Arguments");
                }
            }

        }

        return false;
    }

}
