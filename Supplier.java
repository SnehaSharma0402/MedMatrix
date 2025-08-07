import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class Supplier {
    int supplierID;
    String name;
    String contactInfo;

    private static final String FILE_NAME = "Supplier.csv";

    public Supplier(int supplierID, String name, String contactInfo) {
        this.supplierID = supplierID;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public void supplyItem(int itemID, int quantity) throws IOException {
        InventoryItem item = InventoryItem.getInventoryItemByID(itemID);
        if (item != null) {
            item.addStock(quantity);
        }
    }

    public void save() throws IOException {
        File file = new File(FILE_NAME);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        writer.write(supplierID + "," + name + "," + contactInfo);
        writer.newLine();
        writer.close();
    }

    public static Supplier getSupplierByID(int id) throws IOException {
        File file = new File(FILE_NAME);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            if (Integer.parseInt(row[0]) == id) {
                return new Supplier(
                        Integer.parseInt(row[0]),
                        row[1],
                        row[2]
                );
            }
        }
        reader.close();
        return null;
    }

    public static int generateUniqueSupplierID() throws IOException {
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

    public static java.util.List<Supplier> loadAllSuppliers() throws IOException {
        java.util.List<Supplier> suppliers = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return suppliers;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            Supplier supplier = new Supplier(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    parts[2]
            );
            suppliers.add(supplier);
        }
        reader.close();
        return suppliers;
    }

    public int getSupplierID() {
        return this.supplierID;
    }

    public String getName() {
        return this.name;
    }

    public String getContactInfo() {
        return this.contactInfo;
    }
}