package cz.goldzone.housing.Events;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cz.goldzone.housing.Util.ItemUtil;
import cz.goldzone.housing.GUI.MainGUI;
import cz.goldzone.housing.GUI.ScrollerInventory;
import cz.goldzone.housing.Managers.Pinger;
import cz.goldzone.housing.Plugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class GUIEvents implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getTitle().equalsIgnoreCase("§0§lGZHousing")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if(!(e.getWhoClicked() instanceof Player)) return;
            //Get the current scroller inventory the player is looking at, if the player is looking at one.
            if(!ScrollerInventory.users.containsKey(p.getUniqueId())) return;
            ScrollerInventory inv = ScrollerInventory.users.get(p.getUniqueId());
            if(e.getCurrentItem() == null) return;
            if(e.getCurrentItem().getItemMeta() == null) return;
            if(e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
            //If the pressed item was a nextpage button
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.nextPageName)){
                //If there is no next page, don't do anything
                if(inv.currpage >= inv.pages.size()-1){
                    return;
                }else{
                    //Next page exists, flip the page
                    inv.currpage += 1;
                    p.openInventory(inv.pages.get(inv.currpage));
                }
                //if the pressed item was a previous page button
            }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ScrollerInventory.previousPageName)){
                //If the page number is more than 0 (So a previous page exists)
                if(inv.currpage > 0){
                    //Flip to previous page
                    inv.currpage -= 1;
                    p.openInventory(inv.pages.get(inv.currpage));
                }
            }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lNAHODNA PARCELA")){
                ArrayList<String> list = new ArrayList<>();
                for(Map.Entry<String, Integer> module : Plugin.list.entrySet()) {
                    list.add(module.getKey());
                }
                int size = list.size();
                if(size == 0) {
                    p.sendMessage("§8[§6§lHousing§8] §cVsechny parcely jsou offline");
                    p.closeInventory();
                    return;
                }
                int random = new Random().nextInt(size);
                int maxAtteps = 0;
                String serverName = list.get(random);

                while(!(Pinger.getONLINE(Plugin.ip, Plugin.list.get(serverName)).equals("0/0")) && maxAtteps < 5) {
                    random = new Random().nextInt(size);
                    serverName = list.get(random);
                    maxAtteps++;
                }
                if(maxAtteps == 0) {
                    p.sendMessage("§8[§6§lHousing§8] §c§l[!] §cA jeje neco se pokazilo! Nepodarilo se najit vhodnou parcelu");
                    p.closeInventory();
                    return;
                }
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF(list.get(random));

                p.sendPluginMessage(Plugin.getInstance(), "BungeeCord", out.toByteArray());
            }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lMOJE PARCELA")){
                ByteArrayDataOutput out2 = ByteStreams.newDataOutput();
                out2.writeUTF("Connect");
                out2.writeUTF(p.getName());

                p.sendPluginMessage(Plugin.getInstance(), "BungeeCord", out2.toByteArray());
                if(!(Plugin.list.containsKey(p.getName()))) {
                    Plugin.srv.sendCMD("+start " + p.getName());
                    p.closeInventory();
                    return;
                }
                if(!(Pinger.getONLINE(Plugin.ip, Plugin.list.get(p.getName())).equals("0/0"))) {
                    ByteArrayDataOutput out = ByteStreams.newDataOutput();
                    out.writeUTF("Connect");
                    out.writeUTF(p.getName());

                    p.sendPluginMessage(Plugin.getInstance(), "BungeeCord", out.toByteArray());
                }
            }else if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§4§lPERMANENTNE SMAZAT")){
                p.closeInventory();
                new MainGUI().ConfirmGUI(p);
            } else if(!(ItemUtil.getStringFromItem(e.getCurrentItem()) == null)) {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF(ItemUtil.getStringFromItem(e.getCurrentItem()));

                p.sendPluginMessage(Plugin.getInstance(), "BungeeCord", out.toByteArray());
            }
        }

        if (e.getInventory().getTitle().equalsIgnoreCase("§4§lPotvrzeni")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR)) {
                return;
            }
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§4§l!POTVRDIT!")) {
                Plugin.srv.sendCMD("+delete " + p.getName());
                p.closeInventory();
            }
        }

    }
}
