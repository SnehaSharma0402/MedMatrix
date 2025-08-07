import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class AddInventoryItemWindow extends JFrame {
    public AddInventoryItemWindow() {
        setTitle("Add Inventory Item");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel idLabel = new JLabel("Item ID:");
        JTextField idField = new JTextField();

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel qtyLabel = new JLabel("Quantity:");
        JTextField qtyField = new JTextField();

        JLabel priceLabel = new JLabel("Unit Price:");
        JTextField priceField = new JTextField();

        JButton addButton = new JButton("Add Item");
        JButton cancelButton = new JButton("Cancel");

        add(idLabel);
        add(idField);
        add(nameLabel);
        add(nameField);
        add(qtyLabel);
        add(qtyField);
        add(priceLabel);
        add(priceField);
        add(addButton);
        add(cancelButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int itemID = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    int quantity = Integer.parseInt(qtyField.getText());
                    double unitPrice = Double.parseDouble(priceField.getText());

                    InventoryItem item = new InventoryItem(itemID, name, quantity, unitPrice);
                    item.save();
                    JOptionPane.showMessageDialog(null, "Inventory Item added successfully!");
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error adding inventory item.");
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