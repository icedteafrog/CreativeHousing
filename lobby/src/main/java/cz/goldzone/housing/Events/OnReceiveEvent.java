package cz.goldzone.housing.Events;

import cz.goldzone.housing.Plugin;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class OnReceiveEvent {
    public static void OnReceive(String data) {
        System.out.println(data);
        String[] args = data.split(" ");
        String cmd = args[0];
        switch (cmd) {
            case "-serverlist":
                try {
                    byte[] decoded = Base64.decodeBase64(args[1]);
                    ByteArrayInputStream bais = new ByteArrayInputStream(decoded);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    HashMap<String,Integer> conf = (HashMap<String,Integer>) ois.readObject();
                    Plugin.list = conf;
                    ois.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
        }
    }
}
