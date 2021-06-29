package cz.goldzone.housing.Inventory;

import cz.goldzone.housing.Plugin;
import cz.goldzone.housing.Utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SettingsGUI {
    public void openGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, "§0§lGZHousing Nastaveni");

        if (Plugin.getInstance().getConfig().getBoolean("damage")) {
            inv.setItem(0, ItemUtil.createItem(Material.WOOL, "ZAKAZAT_DAMAGE", (byte) 14));
        } else {
            inv.setItem(0, ItemUtil.createItem(Material.WOOL, "POVOLIT_DAMAGE", (byte) 5));
        }

        if (Plugin.getInstance().getConfig().getBoolean("destroyblock")) {
            inv.setItem(1, ItemUtil.createItem(Material.WOOL, "ZAKAZAT_NICENI_BLOKU", (byte) 14));
        } else {
            inv.setItem(1, ItemUtil.createItem(Material.WOOL, "POVOLIT_NICENI_BLOKU", (byte) 5));
        }

        if (Plugin.getInstance().getConfig().getBoolean("hunger")) {
            inv.setItem(2, ItemUtil.createItem(Material.WOOL, "ZAKAZAT_HUNGER", (byte) 14));
        } else {
            inv.setItem(2, ItemUtil.createItem(Material.WOOL, "POVOLIT_HUNGER", (byte) 5));
        }

        inv.setItem(3, ItemUtil.createItem(Material.GRASS, "NASTAVIT_SPAWN"));
        inv.setItem(8, ItemUtil.createItem(Material.BARRIER, "§4§lZPET"));
        p.openInventory(inv);
    }
}
