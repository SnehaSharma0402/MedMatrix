import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class Medication {
    int medicationID;
    String name;
    String dosage;
    private int inventoryItemID;

    private static final String FILE_NAME = "medications.csv";

    public Medication(int medicationID, String name, String dosage, int inventoryItemID) {
        this.medicationID = medicationID;
        this.name = name;
        this.dosage = dosage;
        this.inventoryItemID = inventoryItemID;
    }

    public void addMedication() throws IOException {
        File file = new File(FILE_NAME);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        String data = medicationID + "," + name + "," + dosage + "," + inventoryItemID;
        writer.write(data);
        writer.newLine();
        writer.close();
    }

    public static Medication getMedicationByID(int id) throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            if (Integer.parseInt(row[0]) == id) {
                int invID = row.length > 3 ? Integer.parseInt(row[3]) : -1;
                reader.close();
                return new Medication(Integer.parseInt(row[0]), row[1], row[2], invID);
            }
        }
        reader.close();
        return null;
    }

    public int getInventoryItemID() {
        return this.inventoryItemID;
    }

    public void setInventoryItemID(int inventoryItemID) throws IOException {
        InventoryItem item = InventoryItem.getInventoryItemByID(inventoryItemID);
        if (item == null) {
            throw new IllegalArgumentException("Inventory Item ID does not exist.");
        }
        this.inventoryItemID = inventoryItemID;
        updateMedicationInCSV();
    }

    private void updateMedicationInCSV() throws IOException {
        java.util.List<String> lines = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String lineContent;
        while ((lineContent = reader.readLine()) != null) {
            String[] parts = lineContent.split(",");
            if (Integer.parseInt(parts[0]) == this.medicationID) {
                lineContent = String.join(",", Arrays.copyOf(parts, 3)) + "," + this.inventoryItemID;
            }
            lines.add(lineContent);
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (String lineToWrite : lines) {
            writer.write(lineToWrite);
            writer.newLine();
        }
        writer.close();
    }

    public static java.util.List<Medication> loadAll() throws IOException {
        java.util.List<Medication> medications = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return medications;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String lineContent;
        while ((lineContent = reader.readLine()) != null) {
            String[] parts = lineContent.split(",");
            if (parts.length < 4) continue;
            medications.add(new Medication(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    parts[2],
                    Integer.parseInt(parts[3])
            ));
        }
        reader.close();
        return medications;
    }

    public int getMedicationID() {
        return this.medicationID;
    }

    public String getName() {
        return this.name;
    }

    public String getDosage() {
        return this.dosage;
    }
}