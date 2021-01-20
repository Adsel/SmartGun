package com.smartgun.shared.file;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class FileManager {
    private final char fieldSeparator;
    private final char delimiter;
    private final String FILENAME_PREFIX = "ExportedData_";

    public FileManager() {
        this.fieldSeparator = ',';
        this.delimiter = '\n';
    }

    public void exportToCsv(String filename, List<CsvRow> csvData) {
        if (csvData.size() > 0) {
            try (PrintWriter writer = new PrintWriter(FILENAME_PREFIX + filename + ".csv")) {
                StringBuilder sb = new StringBuilder();
                addHeader(sb);
                for (CsvRow data: csvData) {
                    System.out.println(data);
                    sb.append(data.getTime());
                    sb.append(this.fieldSeparator);

                    sb.append(data.getName());
                    sb.append(this.fieldSeparator);

                    sb.append(data.getDesc());
                    sb.append(this.fieldSeparator);

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

    private void addHeader(StringBuilder sb) {
        sb.append("Time");
        sb.append(this.fieldSeparator);

        sb.append("Name");
        sb.append(this.fieldSeparator);

        sb.append("Description");
        sb.append(this.fieldSeparator);

        sb.append("Localization");
        sb.append(this.fieldSeparator);

        sb.append(this.delimiter);
    }
}
