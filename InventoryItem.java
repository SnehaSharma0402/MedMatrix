import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class InventoryItem {
    int itemID;
    String name;
    int quantity;
    double unitPrice;
    private java.util.List<Integer> supplierIDs;

    private static final String FILE_NAME = "InventoryItem.csv";
    private static final String SUPPLIER_ASSIGNMENT_FILE = "InventoryItemSuppliers.csv";

    public InventoryItem(int itemID, String name, int quantity, double unitPrice) {
        this.itemID = itemID;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.supplierIDs = new ArrayList<>();
    }

    public void addStock(int amount) throws IOException {
        this.quantity += amount;
        save();
    }

    public void removeStock(int amount) throws IOException {
        this.quantity -= amount;
        save();
    }

    public boolean checkReorder() {
        return this.quantity < 10;
    }

    public void save() throws IOException {
        File file = new File(FILE_NAME);
        boolean exists = false;
        java.util.List<String> lines = new ArrayList<>();
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String lineContent;
            while ((lineContent = reader.readLine()) != null) {
                String[] parts = lineContent.split(",");
                if (Integer.parseInt(parts[0]) == this.itemID) {
                    lineContent = this.itemID + "," + this.name + "," + this.quantity + "," + this.unitPrice;
                    exists = true;
                }
                lines.add(lineContent);
            }
            reader.close();
        }

        if (!exists) {
            lines.add(this.itemID + "," + this.name + "," + this.quantity + "," + this.unitPrice);
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (String lineToWrite : lines) {
            writer.write(lineToWrite);
            writer.newLine();
        }
        writer.close();
    }

    public static InventoryItem getInventoryItemByID(int id) throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            if (Integer.parseInt(row[0]) == id) {
                return new InventoryItem(
                        Integer.parseInt(row[0]),
                        row[1],
                        Integer.parseInt(row[2]),
                        Double.parseDouble(row[3])
                );
            }
        }
        reader.close();
        return null;
    }

    public static java.util.List<InventoryItem> loadAll() throws IOException {
        java.util.List<InventoryItem> items = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return items;
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String lineContent;
        while ((lineContent = reader.readLine()) != null) {
            String[] parts = lineContent.split(",");
            items.add(new InventoryItem(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    Integer.parseInt(parts[2]),
                    Double.parseDouble(parts[3])
            ));
        }
        reader.close();
        return items;
    }

    public void addMedication(int medicationID) throws IOException {
        Medication medication = Medication.getMedicationByID(medicationID);
        if (medication != null) {
            medication.setInventoryItemID(this.itemID);
        }
    }

    public java.util.List<Medication> getMedications() throws IOException {
        java.util.List<Medication> medications = new ArrayList<>();
        java.util.List<Medication> allMedications = Medication.loadAll();
        for (Medication med : allMedications) {
            if (med.getInventoryItemID() == this.itemID) {
                medications.add(med);
            }
        }
        return medications;
    }

    public void addSupplier(int supplierID) throws IOException {
        if (!supplierIDs.contains(supplierID)) {
            supplierIDs.add(supplierID);
            saveSupplierAssignment(supplierID);
        }
    }

    private void saveSupplierAssignment(int supplierID) throws IOException {
        File assignmentFile = new File(SUPPLIER_ASSIGNMENT_FILE);
        BufferedWriter writer = new BufferedWriter(new FileWriter(assignmentFile, true));
        writer.write(this.itemID + "," + supplierID);
        writer.newLine();
        writer.close();
    }

    public java.util.List<Supplier> getSuppliers() throws IOException {
        java.util.List<Supplier> suppliers = new ArrayList<>();
        File assignmentFile = new File(SUPPLIER_ASSIGNMENT_FILE);
        if (!assignmentFile.exists()) {
            return suppliers;
        }
        BufferedReader reader = new BufferedReader(new FileReader(assignmentFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (Integer.parseInt(parts[0]) == this.itemID) {
                int supplierID = Integer.parseInt(parts[1]);
                Supplier supplier = Supplier.getSupplierByID(supplierID);
                if (supplier != null) {
                    suppliers.add(supplier);
                }
            }
        }
        reader.close();
        return suppliers;
    }

    public static int generateUniqueItemID() throws IOException {
        File file = new File(FILE_NAME);
        int maxID = 0;
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id > maxID) maxID = id;
            }
            reader.close();
        }
        return maxID + 1;
    }

    public int getItemID() {
        return this.itemID;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }

    public java.util.List<Integer> getSupplierIDs() {
        return this.supplierIDs;
    }
}