package cz.goldzone.housing.Inventory;

import cz.goldzone.housing.Utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GamemodeGUI {
    public void openINV(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, "§0§lGZHousing Gamemode");

        inv.setItem(1, ItemUtil.createItem(Material.GRASS, "§2§lSURVIVAL"));
        inv.setItem(3, ItemUtil.createItem(Material.BEDROCK, "§e§lADVENTURE"));
        inv.setItem(5, ItemUtil.createItem(Material.STONE, "§c§lCREATIVE"));
        inv.setItem(7, ItemUtil.createItem(Material.GLASS, "§f§lSPECTATOR"));

        p.openInventory(inv);
    }
}
