import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class AddInventoryManagerWindow extends JFrame {
    public AddInventoryManagerWindow() {
        setTitle("Add Inventory Manager");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel idLabel = new JLabel("Manager ID:");
        JTextField idField = new JTextField();

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JButton addButton = new JButton("Add Manager");
        JButton cancelButton = new JButton("Cancel");

        add(idLabel);
        add(idField);
        add(nameLabel);
        add(nameField);
        add(addButton);
        add(cancelButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int managerID = Integer.parseInt(idField.getText());
                    String name = nameField.getText();

                    InventoryManager manager = new InventoryManager(managerID, name);
                    manager.save();
                    JOptionPane.showMessageDialog(null, "Inventory Manager added successfully!");
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error adding inventory manager.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }
}