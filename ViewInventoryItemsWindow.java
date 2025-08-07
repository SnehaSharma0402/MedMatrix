import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class ViewInventoryItemsWindow extends JFrame {
    public ViewInventoryItemsWindow() {
        setTitle("View Inventory Items");
        setSize(700, 400);
        setLocationRelativeTo(null);

        String[] columnNames = {"Item ID", "Name", "Quantity", "Unit Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        try (BufferedReader reader = new BufferedReader(new FileReader("InventoryItem.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading inventory items.");
        }

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}