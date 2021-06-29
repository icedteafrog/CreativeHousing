package cz.goldzone.housing.Inventory;

import cz.goldzone.housing.Utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class MainGUI {
    public void openINV(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, "§0§lGZCreativeHousing");

        inv.setItem(0, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(1, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(2, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(3, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(4, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(5, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(6, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(7, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(8, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(9, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(17, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(18, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(26, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(27, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(35, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(36, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(44, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(45, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(46, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(47, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(48, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(49, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(50, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(51, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(52, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(53, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));

        ArrayList<String> lore = new ArrayList<>();

        lore.add("§7Chces na parcele pracovat s kamaradem?");
        lore.add("§7Nastav mu permise!");
        inv.setItem(13, ItemUtil.createSkull("§6§lPERMISE HRACU", "Steav", lore));
        lore.clear();

        lore.add("§7Chces na parcele zakazat Damage, Hunger atd..?");
        lore.add("§7Neni problem!");
        inv.setItem(22, ItemUtil.createItem(Material.COMMAND, "§6§lNASTAVENI", lore));
        lore.clear();

        lore.add("§7Chces dest ci slunce a den?");
        lore.add("§7Tady si to muzes prepnout podle sebe!");
        inv.setItem(21, ItemUtil.createItem(Material.STAINED_GLASS, "§6§lPOCASI NA PARCELE", lore));
        lore.clear();

        lore.add("§7Chces nastavit hracum gamemode pri pripojeni?");
        lore.add("§7Neni problem!");
        inv.setItem(31, ItemUtil.createItem(Material.GRASS, "§6§lNASTAVENI GAMEMODE", lore));
        lore.clear();

        lore.add("§7Chces popsat svou parcelu?");
        lore.add("§7Tady muzes zaujat ostatni hrace!");
        inv.setItem(29, ItemUtil.createItem(Material.SIGN, "§6§lPOPIS PARCELI", lore));
        lore.clear();

        lore.add("§7Potrebujes WorldEdit?");
        lore.add("§7Tady se dozvis vice!");
        inv.setItem(23, ItemUtil.createItem(Material.WOOD_AXE, "§6§lPOMOC PRI STAVENI", lore));
        lore.clear();

        lore.add("§7Chces mit na parcele parkour?");
        lore.add("§7Tady si muzes nastavit parkour podle sebe!");
        inv.setItem(33, ItemUtil.createItem(Material.FEATHER, "§6§lPARKOUR NASTAVENI", lore));
        lore.clear();

        lore.add("§7Chces stavet parkour a chces davat dlouhe skoky?");
        lore.add("§7Neni problem, jumppad vymrsti hrace 5 bloku do vysky!");
        inv.setItem(37, ItemUtil.createItem(Material.GOLD_PLATE, "§6§lJUMPPAD", lore));
        lore.clear();

        lore.add("§7Dekoracni hlavy?");
        lore.add("§7Neni problem!");
        inv.setItem(40, ItemUtil.createCustomHead("§6§lDEKORACNI HLAVY", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWMxNTE3MGZiZTY0M2Y5OGMyYmFlYmY0MTM3YmU4ZGE3YWVkOTY1ZjI5NWFhYmFjYjVmODg4YzZiMTY1NWIifX19"));
        lore.clear();

        lore.add("§7Nechces aby se ti na parcelu pripojovali cizi hraci?");
        lore.add("§7Neni problem, whitelist to vyresi!");
        inv.setItem(43, ItemUtil.createItem(Material.PAPER, "§6§lWHITELIST", lore));
        lore.clear();

        p.openInventory(inv);
    }
}
