package lv.rvt.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


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


    public static void recordTable(String filename){
        try {
            List<String[]> data = Helper.readCsv(filename);

            String[] headers = data.get(0);
            List<String[]> rows = data.subList(1, data.size());
    
            
            printFormattedTable(rows, headers);
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeRecordTable(String filename, ArrayList<ArrayList<String>> rows, StandardOpenOption... options) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(getFilePath(filename), StandardCharsets.UTF_8, options)) {
            for (ArrayList<String> row : rows) {
                String line = String.join(",", escapeCsvFields(row));
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        }
    }

    private static List<String> escapeCsvFields(ArrayList<String> row) {
    List<String> escaped = new ArrayList<>();
    for (String field : row) {
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            field = field.replace("\"", "\"\"");
            field = "\"" + field + "\"";
        }
        escaped.add(field);
    }
    return escaped;
}


public static void printFormattedTable(List<String[]> rows, String[] headers) {
    int columns = headers.length;
    int[] colWidths = new int[columns];

    
    for (int i = 0; i < columns; i++) {
        colWidths[i] = headers[i].length();
    }

    for (String[] row : rows) {
        for (int i = 0; i < columns; i++) {
            if (i < row.length && row[i] != null) {
                colWidths[i] = Math.max(colWidths[i], row[i].length());
            }
        }
    }

    
    Consumer<String[]> printRow = row -> {
        System.out.print("|");
        for (int i = 0; i < columns; i++) {
            String value = i < row.length ? row[i] : "";
            System.out.print(" " + padRight(value, colWidths[i]) + " |");
        }
        System.out.println();
    };

    printRow.accept(headers);

   
    System.out.print("|");
    for (int width : colWidths) {
        System.out.print("-".repeat(width + 2) + "|");
    }
    System.out.println();

    
    for (String[] row : rows) {
        printRow.accept(row);
    }
}

private static String padRight(String text, int width) {
    return String.format("%-" + width + "s", text);
}

public static void sortRecordsInCsv(String fileName, String sortBy) {
    try {
        List<String[]> rawData = Helper.readCsv(fileName);
        if (rawData.isEmpty()) {
            System.out.println("CSV is empty or missing header.");
            return;
        }

        
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (String[] row : rawData) {
            data.add(new ArrayList<>(List.of(row)));
        }

        
        ArrayList<String> header = data.get(0);
        List<ArrayList<String>> rows = data.subList(1, data.size());

        
        if (sortBy.equalsIgnoreCase("P")) {
            rows.sort((a, b) -> Integer.compare(Integer.parseInt(b.get(1)), Integer.parseInt(a.get(1))));
        } else if (sortBy.equalsIgnoreCase("W")) {
            rows.sort((a, b) -> Integer.compare(Integer.parseInt(b.get(2)), Integer.parseInt(a.get(2))));
        } else {
            System.out.println("Invalid sort type. Use 'P - points' or 'W - wins'.");
            return;
        }

       
        ArrayList<ArrayList<String>> finalData = new ArrayList<>();
        finalData.add(header);
        finalData.addAll(rows);

        
        Helper.writeRecordTable(fileName, finalData, StandardOpenOption.TRUNCATE_EXISTING);


    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
