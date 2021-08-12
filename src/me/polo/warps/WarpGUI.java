package me.polo.warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class WarpGUI implements CommandExecutor, Listener {

    public Main plugin = Main.getPlugin(Main.class);

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("warpgui")){
                p.openInventory(warpGUI(p));
            }
        }

        return false;
    }

    public static Inventory inv;
    public static String inventory_name;
    public static int rows = 3;
    public static int inv_rows = rows * 9;

    public static void initialize(){
        inventory_name = Utils.chat("&l&6Warps Menu");
        inv = Bukkit.createInventory(null, inv_rows);
    }

    public static Inventory warpGUI(Player p) {
        Inventory toReturn = Bukkit.createInventory(p, inv_rows, inventory_name);

        //Inventory inv, (Material material || ItemStack itemStack), int amount, int invSlot, String displayName, String... loreString

        toReturn.setContents(inv.getContents());
        return toReturn;
    }

    public static void clicked(Player p, int slot, ItemStack clicked, Inventory inv){
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) {
        } else {
            if(e.getWhoClicked().getOpenInventory().getTitle().equals(Utils.chat("&l&6Warps Menu"))){
                e.setCancelled(true);

                Player p = (Player) e.getWhoClicked();

                //Bukkit.broadcastMessage(e.getWhoClicked().getName() + " clicked the " + e.getCurrentItem()
                // .getItemMeta().getDisplayName());

                String name = e.getCurrentItem().getItemMeta().getDisplayName();
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
            }
        }
    }
}