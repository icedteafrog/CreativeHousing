package cz.goldzone.node.Managers;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileManager {
    public static void copyNew(String name, String template) {
        File Template = new File("servers/" + name);
        if (!Template.exists())
            try {
                Template.mkdir();
            } catch (Exception exception) {
            }
        File from = new File("template/" + template);
        File to = new File("servers/" + name);
        try {
            copyFolder(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File Template3 = new File("servers/" + name + "/plugins");
        if (!Template3.exists())
            try {
                Template3.mkdir();
            } catch (Exception exception) {
            }
        YAMLCreator.writeConfig(name, "servers/" + name + "/plugins/");
    }

    public static void recursiveDelete(File file) {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            byte b;
            int i;
            File[] arrayOfFile;
            for (i = (arrayOfFile = file.listFiles()).length, b = 0; b < i; ) {
                File f = arrayOfFile[b];
                recursiveDelete(f);
                b++;
            }
        }
        file.delete();
        System.out.println("Deleted file/folder: " + file.getAbsolutePath());
    }

    private static void copyFolder(File sourceFolder, File destinationFolder) throws IOException {
        if (sourceFolder.isDirectory()) {
            if (!destinationFolder.exists()) {
                destinationFolder.mkdir();
                System.out.println("Directory created :: " + destinationFolder);
            }
            String[] files = sourceFolder.list();
            byte b;
            int i;
            String[] arrayOfString1;
            for (i = (arrayOfString1 = files).length, b = 0; b < i; ) {
                String file = arrayOfString1[b];
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);
                copyFolder(srcFile, destFile);
                b++;
            }
        } else {
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
            System.out.println("File copied :: " + destinationFolder);
        }
    }
}
