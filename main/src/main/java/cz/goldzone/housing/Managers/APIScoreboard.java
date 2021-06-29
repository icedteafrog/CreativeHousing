package cz.goldzone.housing.Managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIScoreboard {
    private static final Map<String, APIScoreboard> players = new HashMap<>();
    private final Scoreboard scoreboard;
    private final Objective sidebar;
    Team OWNER;
    Team COOWNER;
    Team MANAGER;
    Team VIP;
    Team HRAC;
    @SuppressWarnings("deprecation")
    private APIScoreboard(Player player) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        sidebar = scoreboard.registerNewObjective("sidebar", "dummy");
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Create Teams
        for (int i = 1; i <= 15; i++) {
            Team team = scoreboard.registerNewTeam("SLOT_" + i);
            team.addEntry(genEntry(i));
        }
        OWNER = scoreboard.registerNewTeam("0OWNER");
        COOWNER = scoreboard.registerNewTeam("1COOWNER");
        MANAGER = scoreboard.registerNewTeam("2MANAGER");
        VIP = scoreboard.registerNewTeam("3VIP");
        HRAC = scoreboard.registerNewTeam("4HRAC");
        OWNER.setPrefix("§4");
        COOWNER.setPrefix("§c");
        MANAGER.setPrefix("§9");
        VIP.setPrefix("§e");
        HRAC.setPrefix("§7");
        player.setScoreboard(scoreboard);
        players.put(player.getName(), this);
    }

    public static boolean hasScore(Player player) {
        return players.containsKey(player.getName());
    }

    public static APIScoreboard createScore(Player player) {
        return new APIScoreboard(player);
    }

    public static APIScoreboard getByPlayer(Player player) {
        return players.get(player.getName());
    }

    public static APIScoreboard removeScore(Player player) {
        return players.remove(player.getName());
    }

    public void setTitle(String title) {
        title = ChatColor.translateAlternateColorCodes('&', title);
        sidebar.setDisplayName(title.length() > 32 ? title.substring(0, 32) : title);
    }

    public void setSlot(int slot, String text) {
        Team team = scoreboard.getTeam("SLOT_" + slot);
        if (team == null) return;
        String entry = genEntry(slot);
        if (!scoreboard.getEntries().contains(entry)) {
            sidebar.getScore(entry).setScore(slot);
        }
        for (Player p2 : Bukkit.getOnlinePlayers()) {
            if (PermissionsEx.getPermissionManager().getUser(p2).getGroupsNames()[0].equals("HRAC")) {
                HRAC.addPlayer(p2);
            } else if (PermissionsEx.getPermissionManager().getUser(p2).getGroupsNames()[0].equals("OWNER")) {
                OWNER.addPlayer(p2);
            } else if (PermissionsEx.getPermissionManager().getUser(p2).getGroupsNames()[0].equals("OWNERWE")) {
                OWNER.addPlayer(p2);
            } else if (PermissionsEx.getPermissionManager().getUser(p2).getGroupsNames()[0].equals("OWNERHV")) {
                OWNER.addPlayer(p2);
            } else if (PermissionsEx.getPermissionManager().getUser(p2).getGroupsNames()[0].equals("COOWNER")) {
                COOWNER.addPlayer(p2);
            } else if (PermissionsEx.getPermissionManager().getUser(p2).getGroupsNames()[0].equals("MANAGER")) {
                MANAGER.addPlayer(p2);
            } else if (PermissionsEx.getPermissionManager().getUser(p2).getGroupsNames()[0].equals("VIP")) {
                VIP.addPlayer(p2);
            }
        }
        setSlot(team, text);
    }

    private void setSlot(Team team, String text) {
        text = ChatColor.translateAlternateColorCodes('&', text);

        String colorChar = String.valueOf(ChatColor.COLOR_CHAR);

        String pre = getFirstSplit(text);
        String suf = getSecondSplit(text);

        // Fix color code splitting
        if (pre.endsWith(colorChar)) {
            pre = pre.substring(0, 15);
            suf = colorChar + suf;
        }
        suf = getFirstSplit(suf.startsWith(colorChar) ? suf : ChatColor.getLastColors(pre) + suf);

        team.setPrefix(pre);
        team.setSuffix(suf);
    }

    public void removeSlot(int slot) {
        String entry = genEntry(slot);
        if (scoreboard.getEntries().contains(entry)) {
            scoreboard.resetScores(entry);
        }
    }

    public void setSlotsFromList(List<String> list) {
        while (list.size() > 15) {
            list.remove(list.size() - 1);
        }
        int slot = list.size();
        if (slot < 15) {
            for (int i = (slot + 1); i <= 15; i++) {
                removeSlot(i);
            }
        }
        for (String line : list) {
            setSlot(slot, line);
            slot--;
        }
    }

    private String genEntry(int slot) {
        return ChatColor.values()[slot].toString();
    }

    private String getFirstSplit(String s) {
        return s.length() > 16 ? s.substring(0, 16) : s;
    }

    private String getSecondSplit(String s) {
        if (s.length() > 32) s = s.substring(0, 32);
        return s.length() > 16 ? s.substring(16) : "";
    }
}

