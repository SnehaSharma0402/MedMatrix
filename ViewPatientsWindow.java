import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class ViewPatientsWindow extends JFrame {
        public ViewPatientsWindow() {
            setTitle("View Patients");
            setSize(600, 400);
            setLocationRelativeTo(null);
    
            String[] columnNames = {"Patient ID", "Name", "Date of Birth", "Gender", "Contact Info", "Department ID"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
    
            try (BufferedReader reader = new BufferedReader(new FileReader("patients.csv"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    tableModel.addRow(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading patients.");
            }
    
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(e -> dispose());
            add(closeButton, BorderLayout.SOUTH);
    
            setVisible(true);
        }
    }    
