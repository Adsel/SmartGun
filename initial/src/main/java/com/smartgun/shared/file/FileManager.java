package com.smartgun.shared.file;

import com.smartgun.model.incident.Event;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class FileManager {
    private final char fieldSeparator;
    private final char delimiter;
    private final String FILENAME_PREFIX = "ExportedData_";
    private final String EMPTY_FIELD = " ";

    public FileManager() {
        this.fieldSeparator = ',';
        this.delimiter = '\n';
    }

    public void exportToCsv(String filename, List<Event> csvData) {
        if (csvData.size() > 0) {
            try (PrintWriter writer = new PrintWriter(FILENAME_PREFIX + filename + ".csv")) {
                StringBuilder sb = new StringBuilder();
                addHeader(sb);
                for (Event data: csvData) {
                    System.out.println(data);
                    sb.append(data.getDate());
                    sb.append(this.fieldSeparator);

                    sb.append(data.getTime());
                    sb.append(this.fieldSeparator);

                    sb.append(data.getX());
                    sb.append(this.fieldSeparator);

                    sb.append(data.getY());
                    sb.append(this.fieldSeparator);

                    sb.append(data.getSectorID());
                    sb.append(this.fieldSeparator);

                    sb.append(data.getType());
                    sb.append(this.fieldSeparator);

                    sb.append(data.getDescription());
                    sb.append(this.fieldSeparator);

                    if (data.getDurationTime() != null) {
                        sb.append(data.getDurationTime());
                        sb.append(this.fieldSeparator);
                    } else {
                        sb.append(EMPTY_FIELD);
                        sb.append(this.fieldSeparator);
                    }

                    sb.append(this.delimiter);
                }
                writer.write(sb.toString());
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void exportInitToCsv(String filename, List<CsvInitRow> csvData) {
        if (csvData.size() > 0) {
            try (PrintWriter writer = new PrintWriter(FILENAME_PREFIX + filename + ".csv")) {
                StringBuilder sb = new StringBuilder();
                addHeaderInit(sb);
                for (CsvInitRow data: csvData) {
                    sb.append(data.getName());
                    sb.append(this.fieldSeparator);

                    sb.append(data.getDesc());
                    sb.append(this.fieldSeparator);

                    sb.append(data.getValue());
                    sb.append(this.fieldSeparator);

                    sb.append(data.getUnit());
                    sb.append(this.delimiter);
                }
                writer.write(sb.toString());
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void addHeaderInit(StringBuilder sb) {
        sb.append("PARAMETER NAME");
        sb.append(this.fieldSeparator);

        sb.append("DESCRIPTION");
        sb.append(this.fieldSeparator);

        sb.append("PARAMETER VALUE");
        sb.append(this.fieldSeparator);

        sb.append("UNIT");
        sb.append(this.fieldSeparator);

        sb.append(this.delimiter);
    }

    private void addHeader(StringBuilder sb) {
        sb.append("DATE");
        sb.append(this.fieldSeparator);

        sb.append("TIME");
        sb.append(this.fieldSeparator);

        sb.append("X");
        sb.append(this.fieldSeparator);

        sb.append("Y");
        sb.append(this.fieldSeparator);

        sb.append("SECTOR ID");
        sb.append(this.fieldSeparator);

        sb.append("TYPE");
        sb.append(this.fieldSeparator);

        sb.append("DESCRIPTION");
        sb.append(this.fieldSeparator);

        sb.append("DURATION TIME");
        sb.append(this.fieldSeparator);

        sb.append(this.delimiter);
    }
}
