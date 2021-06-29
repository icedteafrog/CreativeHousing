package cz.goldzone.node.Managers;

import java.io.FileWriter;
import java.io.IOException;

public class YAMLCreator {
    public static void writeConfig(String serverName, String patch) {
        try {
            FileWriter myWriter = new FileWriter(String.valueOf(patch) + "serverName.yml");
            myWriter.write("serverName: " + serverName);
            myWriter.close();
            System.out.println("Konfiguracni soubor se vytvoril!");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
