import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class ViewInventoryManagersWindow extends JFrame {
    public ViewInventoryManagersWindow() {
        setTitle("View Inventory Managers");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        try {
            java.util.List<InventoryManager> managers = InventoryManager.loadAll();
            for (InventoryManager m : managers) {
                textArea.append("ID: " + m.managerID + ", Name: " + m.name + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            textArea.setText("Error loading inventory managers.");
        }

        JButton closeButton = new JButton("Close");
        add(closeButton, BorderLayout.SOUTH);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }
}