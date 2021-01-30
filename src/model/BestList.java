package model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BestList {

    private ArrayList<String> importList;
    private ArrayList<ScoreListEntry> exportList;

    public BestList() {
        this.importList = new ArrayList<>();
        this.exportList = new ArrayList<>();
    }

    public void addToBestListCsv(ScoreListEntry entry, String pathname) {
        if (new File(pathname).exists()) {
            try {
                FileWriter fileWriter = new FileWriter(new File(pathname), true);
                fileWriter.write(entry.toString() + ";");
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readBestListCsvIntoImportList(String pathname) {
        if (new File(pathname).exists()) {
            importList.clear();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname),
                    StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] entries;
                    entries = line.split(";");
                    if (!line.isEmpty()) {
                        Collections.addAll(importList, entries);
                    }
                }
            } catch (IOException ioe) {
                System.out.println(Arrays.toString(ioe.getStackTrace()));
            }
        }
    }

    public ArrayList<ScoreListEntry> convertImportListToExportList() {
        exportList.clear();
        for (String string : importList) {
            String[] parts = string.split("/");
            String pointsString = parts[0].trim();
            int points = Integer.parseInt(pointsString);
            String name = parts[1].trim();
            ScoreListEntry scoreListEntry = new ScoreListEntry(points, name);
            exportList.add(scoreListEntry);
        }
        exportList.sort(Collections.reverseOrder());
        return exportList;
    }
}
