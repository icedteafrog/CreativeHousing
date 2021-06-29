package cz.goldzone.housing.Managers;

import cz.goldzone.housing.Plugin;

public class VoteManager {

    public static void addVote() {
        Mysql mysql = Plugin.mysql;
        int now = mysql.getVotes();
        now++;
        mysql.updateVotes(now);
    }

}
