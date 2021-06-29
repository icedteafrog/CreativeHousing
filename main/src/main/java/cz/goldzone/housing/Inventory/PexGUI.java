package cz.goldzone.housing.Inventory;

import cz.goldzone.housing.Plugin;
import cz.goldzone.housing.Utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.HashMap;

public class PexGUI {
    public static HashMap<Player, String> groups = new HashMap<>();

    public void pexGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, "§0§lGZHousing PEX");

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

        lore.add("§7Delat hologramy");
        lore.add("§7Pristup k /hdb");
        lore.add("§7Muze menit pocasi");
        lore.add("§7Portovat hrace");
        lore.add("§7Nastavovat si gamemode");
        lore.add("§7Nastavovat parkour");
        lore.add("§7Muze litat");
        inv.setItem(11, ItemUtil.createItem(Material.SIGN, "§4§lCO-OWNER", lore));
        lore.clear();

        lore.add("§7Muze menit pocasi");
        lore.add("§7Portovat hrace");
        lore.add("§7Nastavovat si gamemode");
        lore.add("§7Muze litat");
        inv.setItem(13, ItemUtil.createItem(Material.SIGN, "§9§lMANGER", lore));
        lore.clear();

        lore.add("§7Muze litat");
        inv.setItem(15, ItemUtil.createItem(Material.SIGN, "§e§lVIP", lore));
        lore.clear();

        inv.setItem(40, ItemUtil.createItem(Material.ANVIL, "§6§lVLASTNI GROUPKY §c(Nedostupne)", lore));

        p.openInventory(inv);

    }

    public void groupEditGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "§0§lGZHousing GroupEdit");

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
        inv.setItem(19, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(20, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(21, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(22, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(23, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(24, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(25, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));
        inv.setItem(26, ItemUtil.createItem(Material.STAINED_GLASS_PANE, " ", (byte) 7));

        inv.setItem(11, ItemUtil.createItem(Material.REDSTONE, "§4§lODEBRAT HRACE"));
        inv.setItem(15, ItemUtil.createItem(Material.EMERALD, "§a§lPRIDAT HRACE"));

        p.openInventory(inv);
    }

    public void addPGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, "§0§lGZHousing AddPlayer");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("§aKliknutim pridas");
        for (Player ps : Bukkit.getOnlinePlayers()) {
            if (!(PermissionsEx.getUser(ps).inGroup(groups.get(p))) && !(Plugin.serverName.contains(ps.getName()))) {
                System.out.println(PermissionsEx.getUser(ps).inGroup(groups.get(p)));
                inv.addItem(ItemUtil.createSkull(ps.getName(), ps.getName(), lore));
            }
        }
        lore.clear();

        inv.setItem(49, ItemUtil.createItem(Material.EMERALD, "§4§lZPET"));

        p.openInventory(inv);
    }

    public void removePGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, "§0§lGZHousing RemovePlayer");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("§cKliknutim odeberes");
        for (PermissionUser s : PermissionsEx.getPermissionManager().getGroup(groups.get(p)).getUsers()) {
            inv.addItem(ItemUtil.createSkull(s.getName(), s.getName(), lore));
        }
        lore.clear();

        inv.setItem(49, ItemUtil.createItem(Material.EMERALD, "§4§lZPET"));

        p.openInventory(inv);
    }
}
