package me.polo.warps;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WarpGUI implements CommandExecutor, Listener {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("warpgui")){
                if(p.isOp()){
                    warpGUI(p);
                } else {
                    p.sendMessage(ChatColor.RED + "Insufficient Permission");
                }
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
        Utils.createItemMaterial(inv, Material.DIAMOND_AXE, 1, 1, "&l&4Test", "&l&6The secrets lie within");

        toReturn.setContents(inv.getContents());
        return toReturn;
    }

    public static void clicked(Player p, int slot, ItemStack clicked, Inventory inv){}

}