import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class InventoryManager {
    int managerID;
    String name;
    private java.util.List<Integer> managedItemIDs;

    private static final String FILE_NAME = "InventoryManager.csv";
    private static final String ASSIGNMENT_FILE = "InventoryManagerItems.csv";

    public InventoryManager(int managerID, String name) {
        this.managerID = managerID;
        this.name = name;
        this.managedItemIDs = new ArrayList<>();
    }

    public void addInventoryItem(InventoryItem item) {
        try {
            item.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateInventoryItem(InventoryItem item) {
        try {
            item.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public java.util.List<InventoryItem> viewInventory() throws IOException {
        return InventoryItem.loadAll();
    }

    public void save() throws IOException {
        File file = new File(FILE_NAME);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        writer.write(managerID + "," + name);
        writer.newLine();
        writer.close();
    }

    public static java.util.List<InventoryManager> loadAll() throws IOException {
        java.util.List<InventoryManager> managers = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return managers;
        }

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            managers.add(new InventoryManager(Integer.parseInt(parts[0]), parts[1]));
        }
        reader.close();
        return managers;
    }

    public void assignInventoryItem(int itemID) throws IOException {
        if (!managedItemIDs.contains(itemID)) {
            managedItemIDs.add(itemID);
            saveAssignment(itemID);
        }
    }

    private void saveAssignment(int itemID) throws IOException {
        File assignmentFile = new File(ASSIGNMENT_FILE);
        BufferedWriter writer = new BufferedWriter(new FileWriter(assignmentFile, true));
        writer.write(this.managerID + "," + itemID);
        writer.newLine();
        writer.close();
    }

    public java.util.List<InventoryItem> getManagedInventoryItems() throws IOException {
        java.util.List<InventoryItem> managedItems = new ArrayList<>();
        File assignmentFile = new File(ASSIGNMENT_FILE);
        if (!assignmentFile.exists()) {
            return managedItems;
        }
        BufferedReader reader = new BufferedReader(new FileReader(assignmentFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (Integer.parseInt(parts[0]) == this.managerID) {
                int itemID = Integer.parseInt(parts[1]);
                InventoryItem item = InventoryItem.getInventoryItemByID(itemID);
                if (item != null) {
                    managedItems.add(item);
                }
            }
        }
        reader.close();
        return managedItems;
    }

    public void manageInventoryItem(int itemID) throws IOException {
        assignInventoryItem(itemID);
    }

    public int getManagerID() {
        return this.managerID;
    }

    public String getName() {
        return this.name;
    }
}
