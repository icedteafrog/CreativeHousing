package cz.goldzone.node.Server;

import cz.goldzone.node.Main;

import java.io.IOException;

public class BackSend {
    public static void addServer(String name, String port) {
        try {
            Main.srv.sendChatMessageToAll("-serveradd " + name + " " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeServer(String name) {
        try {
            Main.srv.sendChatMessageToAll("-serverremove " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendCrash(String name) {
        try {
            Main.srv.sendChatMessageToAll("-crashreport " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendServers(String string) {
        try {
            Main.srv.sendChatMessageToAll("-serverlist " + string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendOnline(String name) {
        try {
            Main.srv.sendChatMessageToAll("-online " + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageToPlayer(String player, String chat) {
        try {
            Main.srv.sendChatMessageToAll("-msgtoplayer " + player + " " + chat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
