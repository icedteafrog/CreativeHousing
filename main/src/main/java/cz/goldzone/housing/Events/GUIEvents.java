package cz.goldzone.housing.Events;

import cz.goldzone.housing.Inventory.*;
import cz.goldzone.housing.Plugin;
import cz.goldzone.housing.Utils.ItemUtil;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class GUIEvents implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getTitle().equalsIgnoreCase("§0§lGZCreativeHousing")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lPARKOUR NASTAVENI")) {
                if (!(p.hasPermission("housing.parkour"))) {
                    p.sendMessage(Plugin.prefix + " §cNa tohle nemas opravneni!");
                    return;
                }
                p.closeInventory();
                new ParkourGUI().parkourGUI(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lJUMPPAD")) {
                if (!(p.hasPermission("housing.jumppad"))) {
                    p.sendMessage(Plugin.prefix + " §cNa tohle nemas opravneni!");
                    return;
                }
                p.getInventory().addItem(ItemUtil.createItem(Material.GOLD_PLATE, "§eJumpPad (Poloz)"));
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lWHITELIST")) {
                if (!(p.hasPermission("housing.whitelist"))) {
                    p.sendMessage(Plugin.prefix + " §cNa tohle nemas opravneni!");
                    return;
                }
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + p.getName());
                p.sendMessage(Plugin.prefix + " §aWhitelist ->");
                p.sendMessage(Plugin.prefix + " §e/whitelist on - Zapne whitelist");
                p.sendMessage(Plugin.prefix + " §e/whitelist off - Vypne whitelist");
                p.sendMessage(Plugin.prefix + " §e/whitelist add <hrac> - Prida hrace na whitelist");
                p.sendMessage(Plugin.prefix + " §e/whitelist remove <hrac> - Odebere hrace z whitelist");
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lNASTAVENI")) {
                if (!(p.hasPermission("housing.settings"))) {
                    p.sendMessage(Plugin.prefix + " §cNa tohle nemas opravneni!");
                    return;
                }
                p.closeInventory();
                new SettingsGUI().openGUI(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lPOMOC PRI STAVENI")) {
                if (PermissionsEx.getUser(p.getName()).inGroup("OWNERWE")) {
                    p.getInventory().addItem(new ItemStack(Material.WOOD_AXE));
                } else {
                    p.sendMessage(Plugin.prefix + " §7Pro pouzivani WorldEditu musis vlastni §6§lOVERLORD §7nebo si na webu zakoupit permisi!");
                }
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lPERMISE HRACU")) {
                if (!(p.hasPermission("housing.perms"))) {
                    p.sendMessage(Plugin.prefix + " §cNa tohle nemas opravneni!");
                    return;
                }
                p.closeInventory();
                new PexGUI().pexGUI(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lNASTAVENI GAMEMODE")) {
                if (!(p.hasPermission("housing.gm"))) {
                    p.sendMessage(Plugin.prefix + " §cNa tohle nemas opravneni!");
                    return;
                }
                p.closeInventory();
                new GamemodeGUI().openINV(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lDEKORACNI HLAVY")) {
                p.closeInventory();
                p.chat("/hdb");
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lPOPIS PARCELI")) {
                if (!(p.hasPermission("housing.motd"))) {
                    p.sendMessage(Plugin.prefix + " §cNa tohle nemas opravneni!");
                    return;
                }
                new AnvilGUI(Plugin.getInstance(), (Player) e.getWhoClicked(), "Zde napis popis", (player, reply) -> {
                    Plugin.getInstance().getConfig().set("Tag", reply);
                    Plugin.getInstance().saveConfig();
                    p.sendMessage(Plugin.prefix + " §a§lHotovo! §aPopis se zmenil na §7" + reply.replaceAll("&", "§"));
                    Plugin.mysql.updateMotd(reply);
                    return null;
                });
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lPOCASI NA PARCELE")) {
                if (!(p.hasPermission("housing.weather"))) {
                    p.sendMessage(Plugin.prefix + " §cNa tohle nemas opravneni!");
                    return;
                }
                p.closeInventory();
                new TimeGUI().openINV(p);
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§0§lGZHousing Weather")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lVypnout dest")) {
                p.getWorld().setStorm(false);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lZapnout dest")) {
                p.getWorld().setStorm(true);
                p.getWorld().setWeatherDuration(Integer.MAX_VALUE);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lVypnout stridani dne a noci")) {
                p.getWorld().setGameRuleValue("doDaylightCycle", "false");
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lZapnout stridani dne a noci")) {
                p.getWorld().setGameRuleValue("doDaylightCycle", "true");
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§e§lDEN")) {
                p.getWorld().setTime(5000);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§9§lNOC")) {
                p.getWorld().setTime(20000);
            }
            p.closeInventory();
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§0§lGZHousing PEX")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4§lCO-OWNER")) {
                if (PexGUI.groups.containsKey(p)) {
                    PexGUI.groups.replace(p, "COOWNER");
                } else {
                    PexGUI.groups.put(p, "COOWNER");
                }
                p.closeInventory();
                new PexGUI().groupEditGUI(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§9§lMANGER")) {
                if (PexGUI.groups.containsKey(p)) {
                    PexGUI.groups.replace(p, "MANAGER");
                } else {
                    PexGUI.groups.put(p, "MANAGER");
                }
                p.closeInventory();
                new PexGUI().groupEditGUI(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§e§lVIP")) {
                if (PexGUI.groups.containsKey(p)) {
                    PexGUI.groups.replace(p, "VIP");
                } else {
                    PexGUI.groups.put(p, "VIP");
                }
                p.closeInventory();
                new PexGUI().groupEditGUI(p);
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§0§lGZHousing GroupEdit")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4§lODEBRAT HRACE")) {
                p.closeInventory();
                new PexGUI().removePGUI(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§a§lPRIDAT HRACE")) {
                p.closeInventory();
                new PexGUI().addPGUI(p);
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§0§lGZHousing AddPlayer")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4§lZPET")) {
                p.closeInventory();
                new PexGUI().pexGUI(p);
                return;
            }
            PermissionUser user = PermissionsEx.getUser(e.getCurrentItem().getItemMeta().getDisplayName());
            String[] st = {PexGUI.groups.get(p), "Hrac"};
            user.setGroups(st);
            p.closeInventory();
            new PexGUI().addPGUI(p);
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§0§lGZHousing RemovePlayer")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4§lZPET")) {
                p.closeInventory();
                new PexGUI().pexGUI(p);
                return;
            }
            PermissionUser user = PermissionsEx.getUser(e.getCurrentItem().getItemMeta().getDisplayName());
            user.removeGroup(PexGUI.groups.get(p));
            p.closeInventory();
            new PexGUI().removePGUI(p);
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§0§lGZHousing Gamemode")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§2§lSURVIVAL")) {
                Plugin.getInstance().getConfig().set("Gamemode", 0);
                Plugin.getInstance().saveConfig();
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§e§lADVENTURE")) {
                Plugin.getInstance().getConfig().set("Gamemode", 2);
                Plugin.getInstance().saveConfig();
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§c§lCREATIVE")) {
                Plugin.getInstance().getConfig().set("Gamemode", 1);
                Plugin.getInstance().saveConfig();
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§f§lSPECTATOR")) {
                Plugin.getInstance().getConfig().set("Gamemode", 3);
                Plugin.getInstance().saveConfig();
                p.closeInventory();
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§0§lGZHousing Parkour")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE)) {
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lVYTVORIT PARKOUR")) {
                int count = 0;
                if (!(Plugin.getInstance().getParkour().getString("Parkours") == null)) {
                    for (String key : Plugin.getInstance().parConfig.getConfigurationSection("Parkours").getKeys(false)) {
                        count++;
                    }
                }
                if (count >= 15) {
                    p.sendMessage(Plugin.prefix + " §cMuzes maximalne vytvorit 15 PARKOURS");
                    return;
                }
                new AnvilGUI(Plugin.getInstance(), (Player) e.getWhoClicked(), "Zde napis nazev (Bez mezer a barev)", (player, reply) -> {
                    if (reply.contains(" ")) {
                        return Plugin.prefix + " §cBez mezer!";
                    }
                    Plugin.getInstance().getParkour().set("Parkours." + reply + ".name", reply);
                    Plugin.getInstance().saveParkour();
                    p.sendMessage(Plugin.prefix + " §a§lHotovo! §aVytvoril jsi parkour §7" + reply);
                    return null;
                });
                return;
            }
            String name = e.getCurrentItem().getItemMeta().getDisplayName();
            if (ParkourGUI.nowpar.containsKey(p)) {
                ParkourGUI.nowpar.replace(p, name);
            } else {
                ParkourGUI.nowpar.put(p, name);
            }
            p.closeInventory();
            new ParkourGUI().selectModGUI(p);
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§0§lGZHousing SelMOD")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§9§lZOBRAZIT LIST HRACU")) {
                p.closeInventory();
                new ParkourGUI().listINV(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§b§lNASTAVENI ODMENY")) {
                ItemStack i = p.getInventory().getItemInMainHand();
                Plugin.getInstance().getParkour().set("Parkours." + ParkourGUI.nowpar.get(p) + ".reward", ItemUtil.toBase64(i));
                Plugin.getInstance().saveParkour();
                p.sendMessage(Plugin.prefix + " §a§lHotovo!§a Item byl nastaven");
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lEDITOVAT MAPU")) {
                p.getInventory().clear();
                p.getInventory().addItem(ItemUtil.createItem(Material.STONE_PLATE, "§7Start (Poloz)"));
                p.getInventory().addItem(ItemUtil.createItem(Material.IRON_PLATE, "§eCheckPoint (Poloz)"));
                p.getInventory().addItem(ItemUtil.createItem(Material.GOLD_PLATE, "§bKonec parkouru (Poloz)"));
                p.closeInventory();
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4§lSMAZAT")) {
                Plugin.getInstance().getParkour().set("Parkours." + ParkourGUI.nowpar.get(p), null);
                Plugin.getInstance().saveParkour();
                p.closeInventory();
                new ParkourGUI().parkourGUI(p);
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§0§lGZHousing Nastaveni")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("ZAKAZAT_DAMAGE")) {
                Plugin.getInstance().getConfig().set("damage", false);
                p.closeInventory();
                new SettingsGUI().openGUI(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("POVOLIT_DAMAGE")) {
                Plugin.getInstance().getConfig().set("damage", true);
                p.closeInventory();
                new SettingsGUI().openGUI(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("ZAKAZAT_NICENI_BLOKU")) {
                Plugin.getInstance().getConfig().set("destroyblock", false);
                p.closeInventory();
                new SettingsGUI().openGUI(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("POVOLIT_NICENI_BLOKU")) {
                Plugin.getInstance().getConfig().set("destroyblock", true);
                p.closeInventory();
                new SettingsGUI().openGUI(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("ZAKAZAT_HUNGER")) {
                Plugin.getInstance().getConfig().set("hunger", false);
                p.closeInventory();
                new SettingsGUI().openGUI(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("POVOLIT_HUNGER")) {
                Plugin.getInstance().getConfig().set("hunger", true);
                p.closeInventory();
                new SettingsGUI().openGUI(p);
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("NASTAVIT_SPAWN")) {
                Location location = p.getLocation();
                Plugin.getInstance().getConfig().set("spawn", location.getBlockX() + "/" + location.getBlockY() + "/" +
                        location.getBlockZ() + "/" + location.getWorld().getName());
                p.sendMessage(Plugin.prefix + " §a§lHotovo! §aSpawn byl nastaven!");
                p.closeInventory();
            }
            Plugin.getInstance().saveConfig();
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4§lZPET")) {
                p.closeInventory();
                new MainGUI().openINV(p);
            }
        }
        if (e.getInventory().getTitle().equalsIgnoreCase("§c§lGZHousing ParList")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if (!(e.getWhoClicked() instanceof Player)) return;
            //Get the current scroller inventory the player is looking at, if the player is looking at one.
            if (!ParkourScoller.users.containsKey(p.getUniqueId())) return;
            ParkourScoller inv = ParkourScoller.users.get(p.getUniqueId());
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getItemMeta() == null) return;
            if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
            //If the pressed item was a nextpage button
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ParkourScoller.nextPageName)) {
                //If there is no next page, don't do anything
                if (inv.currpage >= inv.pages.size() - 1) {
                    return;
                } else {
                    //Next page exists, flip the page
                    inv.currpage += 1;
                    p.openInventory(inv.pages.get(inv.currpage));
                }
                //if the pressed item was a previous page button
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ParkourScoller.previousPageName)) {
                //If the page number is more than 0 (So a previous page exists)
                if (inv.currpage > 0) {
                    //Flip to previous page
                    inv.currpage -= 1;
                    p.openInventory(inv.pages.get(inv.currpage));
                }
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4§lZPET")) {
                p.closeInventory();
                new ParkourGUI().selectModGUI(p);
            }
        }
    }

}
