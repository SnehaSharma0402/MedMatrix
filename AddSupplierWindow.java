import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class AddSupplierWindow extends JFrame {
    public AddSupplierWindow() {
        setTitle("Add Supplier");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel idLabel = new JLabel("Supplier ID:");
        JTextField idField = new JTextField();

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel contactLabel = new JLabel("Contact Info:");
        JTextField contactField = new JTextField();

        JButton addButton = new JButton("Add Supplier");
        JButton cancelButton = new JButton("Cancel");

        add(idLabel);
        add(idField);
        add(nameLabel);
        add(nameField);
        add(contactLabel);
        add(contactField);
        add(addButton);
        add(cancelButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int supplierID = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String contact = contactField.getText();

                    Supplier supplier = new Supplier(supplierID, name, contact);
                    supplier.save();
                    JOptionPane.showMessageDialog(null, "Supplier added successfully!");
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error adding supplier.");
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
