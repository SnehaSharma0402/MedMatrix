import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class AddDoctorWindow extends JFrame {
        public AddDoctorWindow() {
            setTitle("Add Doctor");
            setSize(400, 300);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(5, 2, 10, 10));

            add(new JLabel("Doctor ID:"));
            JTextField idField = new JTextField();
            add(idField);

            add(new JLabel("Name:"));
            JTextField nameField = new JTextField();
            add(nameField);

            add(new JLabel("Specialization:"));
            JTextField specializationField = new JTextField();
            add(specializationField);

            add(new JLabel("Contact Info:"));
            JTextField contactField = new JTextField();
            add(contactField);

            add(new JLabel("Department ID:"));
            JTextField deptField = new JTextField();
            add(deptField);

            JButton addButton = new JButton("Add Doctor");
            addButton.addActionListener(e -> {
                try {
                    int doctorID = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String specialization = specializationField.getText();
                    String contact = contactField.getText();
                    int deptID = Integer.parseInt(deptField.getText());

                    Doctor doctor = new Doctor(doctorID, name, specialization, contact, deptID);
                    doctor.addDoctor();
                    JOptionPane.showMessageDialog(this, "Doctor added successfully!");
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error adding doctor.");
                }
            });
            add(addButton);

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> dispose());
            add(cancelButton);

            setVisible(true);
        }
    }
