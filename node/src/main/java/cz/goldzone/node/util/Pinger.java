package cz.goldzone.node.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Pinger {
    public static String getONLINE(String ip, int port) {
        String returnString = null;
        try {
            Socket sock = new Socket();
            sock.setSoTimeout(100);
            sock.connect(new InetSocketAddress(ip, port), 100);
            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream());
            out.write(254);
            StringBuffer str = new StringBuffer();
            int b;
            while ((b = in.read()) != -1) {
                if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24)
                    str.append((char) b);
            }
            String[] data = str.toString().split("§");
            String serverMotd = data[0];
            int onlinePlayers = Integer.parseInt(data[1]);
            int maxPlayers = Integer.parseInt(data[2]);
            returnString = String.format(String.valueOf(onlinePlayers), new Object[0]);
            sock.close();
        } catch (ConnectException e) {
            returnString = "0";
        } catch (UnknownHostException e) {
            returnString = "0";
        } catch (IOException e) {
            returnString = "0";
        }
        return returnString;
    }
}
