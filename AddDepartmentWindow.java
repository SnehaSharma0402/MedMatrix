import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class AddDepartmentWindow extends JFrame {
    public AddDepartmentWindow() {
        setTitle("Add Department");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel idLabel = new JLabel("Department ID:");
        JTextField idField = new JTextField();

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel descLabel = new JLabel("Description:");
        JTextField descField = new JTextField();

        JButton addButton = new JButton("Add Department");
        JButton cancelButton = new JButton("Cancel");

        add(idLabel);
        add(idField);
        add(nameLabel);
        add(nameField);
        add(descLabel);
        add(descField);
        add(addButton);
        add(cancelButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int deptID = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String desc = descField.getText();

                    Department dept = new Department(deptID, name, desc);
                    dept.addDepartment();
                    JOptionPane.showMessageDialog(null, "Department added successfully!");
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error adding department.");
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

