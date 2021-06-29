package cz.goldzone.node.util;

import cz.goldzone.node.Enums.Lang;
import cz.goldzone.node.Main;

import java.io.IOException;

public class PMSG {
    public static void sendMSG(String player, Lang msg) {
        try {
            Main.srv.sendChatMessageToAll("-sendtoplayer " + player + " " + Lang.PREFIX.getMessage() + " " + msg.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
