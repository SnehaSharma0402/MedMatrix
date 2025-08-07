import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class AddPatientWindow extends JFrame {
        public AddPatientWindow() {
            setTitle("Add Patient");
            setSize(400, 300);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(7, 2, 10, 10));

            add(new JLabel("Patient ID:"));
            JTextField idField = new JTextField();
            add(idField);

            add(new JLabel("Name:"));
            JTextField nameField = new JTextField();
            add(nameField);

            add(new JLabel("Date of Birth (dd-MM-yyyy):"));
            JTextField dobField = new JTextField();
            add(dobField);

            add(new JLabel("Gender:"));
            JTextField genderField = new JTextField();
            add(genderField);

            add(new JLabel("Contact Info:"));
            JTextField contactField = new JTextField();
            add(contactField);

            add(new JLabel("Department ID:"));
            JTextField deptField = new JTextField();
            add(deptField);

            JButton addButton = new JButton("Add Patient");
            addButton.addActionListener(e -> {
                try {
                    int patientID = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date dob = sdf.parse(dobField.getText());
                    String gender = genderField.getText();
                    String contact = contactField.getText();
                    int deptID = Integer.parseInt(deptField.getText());

                    Patient patient = new Patient(patientID, name, dob, gender, contact, deptID);
                    patient.register();
                    JOptionPane.showMessageDialog(this, "Patient added successfully!");
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error adding patient.");
                }
            });
            add(addButton);

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> dispose());
            add(cancelButton);

            setVisible(true);
        }
    }
