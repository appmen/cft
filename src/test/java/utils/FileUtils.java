package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils
{
    public static String createTempDir(String prefix)
    {
        try {
            Path temp = Files.createTempDirectory(prefix);
            temp.toFile().deleteOnExit();
            return temp.toAbsolutePath().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getfileName(String directory) {
        File folder = new File(directory);
        File[] listOfFiles;

        for (int j = 0; j < 20; j++) {
            listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".zip") && listOfFiles[i].length() > 0) {
                    return listOfFiles[i].getName();
                }
            }
        }
        return null;
    }
    
    public static String getFileContent(String extractedFile)
    {
        BufferedReader br = null;
        String fileContent = "";
        try {
            br = new BufferedReader(new FileReader(extractedFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);

                line = br.readLine();
            }
            fileContent = sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                br.close();
            } catch (Exception e) {
            }
        }
        return fileContent;
    }
}
