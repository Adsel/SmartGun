package com.smartgun.shared.file;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class FileManager {
    private final char fieldSeparator;
    private final char delimiter;

    public FileManager() {
        this.fieldSeparator = ',';
        this.delimiter = '\n';
    }

    public void exportToCsv(String filename, List<CsvRow> csvData) {

        try (PrintWriter writer = new PrintWriter("test.csv")) {

            StringBuilder sb = new StringBuilder();
            for (CsvRow data: csvData) {
                // TIME
                sb.append(data.getTime());
                sb.append(this.fieldSeparator);

                // NAME
                sb.append(data.getName());
                sb.append(this.fieldSeparator);

                // DESCRIPTION
                sb.append(data.getDesc());
                sb.append(this.fieldSeparator);

                // POINT
                sb.append(data.getPoint());
                sb.append(this.fieldSeparator);

                sb.append(this.delimiter);
            }
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
