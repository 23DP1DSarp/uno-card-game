package lv.rvt.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;


public class Helper {

    public static BufferedReader getReader(String filename) throws IOException {
       return Files.newBufferedReader(getFilePath(filename));
    }

    public static BufferedWriter getWriter(String filename, StandardOpenOption option) throws IOException {
       return Files.newBufferedWriter(getFilePath(filename), option);
    }

    private static Path getFilePath(String filename) throws FileNotFoundException {
        Path filePath = Paths.get("data", filename);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + filename);
        }
        return filePath;
    }


    public static List<String[]> readCsv(String filename) throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader reader = getReader(filename)) {
            String line;
            while ((line = reader.readLine()) != null) {
                
                String[] values = line.split(",");
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].trim();
                }
                rows.add(values);
            }
        }
        return rows;
    }


    public static void recordTable(){
        {
    
            try {
                List<String[]> data = Helper.readCsv("data.csv");
                for (String[] row : data) {
                    System.out.println(String.join(" | ", row));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
