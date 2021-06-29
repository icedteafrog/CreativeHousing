package cz.goldzone.housing.Managers;

import cz.goldzone.housing.Plugin;
import cz.goldzone.housing.Utils.APIActionBar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class ScoreboardManager {
    private List<String> lines;
    private String title;

    private String timeFormat = "dd/MM/yyyy HH:mm";

    private Timer timer;

    public ScoreboardManager() {
        init();
    }

    private void init() {
        reload();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach((p) -> {
                    Plugin.getInstance().getScoreBoard().updateBoard(p);
                    if (p.hasPermission("housing.*")) {
                        APIActionBar.sendActionbar(p, "&ePro zobrazeni moznosti pouzij &6/menu");
                    } else {
                        APIActionBar.sendActionbar(p, "&eZahlasuj pro tuto parcelu pomoci &6/vote");
                    }
                });
            }
        }, 0L, 1000L);
    }

    public void createBoard(Player player) {
        APIScoreboard board = APIScoreboard.getByPlayer(player);
        if (board == null) {
            board = APIScoreboard.createScore(player);
        }
        board.setTitle(title);
        board.setSlotsFromList(replace(player, lines));
    }

    public void updateBoard(Player player) {
        APIScoreboard board = APIScoreboard.getByPlayer(player);
        if (board == null) return;
        board.setTitle(title);
        board.setSlotsFromList(replace(player, lines));
    }

    public void removeBoard(Player player) {
        APIScoreboard.removeScore(player);
    }

    public void reload() {
        this.title = "       §6§lHousing     ";
        lines = new ArrayList<>();
        this.lines.add(" ");
        this.lines.add("§7Parcela: §e" + Plugin.serverName);
        this.lines.add("§7Hraci: §e{size}");
        this.lines.add("§7Hodnoceni: §e{rating}");
        this.lines.add("§7Rank: §e{rank}");
        this.lines.add(" ");
        this.lines.add("    §6play.gold-zone.cz");
        Bukkit.getOnlinePlayers().forEach(this::updateBoard);
    }

    public void destroy() {
        timer.cancel();
        Bukkit.getOnlinePlayers().forEach(this::removeBoard);
        lines.clear();
    }

    private String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    private String replace(Player player, String s) {
        String result = s
                .replace("{rank}", PermissionsEx.getPermissionManager().getUser(player.getName()).getGroupsNames()[0])
                .replace("{size}", String.valueOf(Bukkit.getOnlinePlayers().size()))
                .replace("{rating}", String.valueOf(Plugin.mysql.getVotes()));
        return result;
    }

    private List<String> replace(Player player, List<String> list) {
        return list.stream().map(s -> replace(player, s)).collect(Collectors.toList());
    }

    private List<String> color(List<String> list) {
        return list.stream().map(this::color).collect(Collectors.toList());
    }
}
