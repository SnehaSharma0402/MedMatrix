import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class AddAppointmentWindow extends JFrame {
        public AddAppointmentWindow() {
            setTitle("Add Appointment");
            setSize(400, 300);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(5, 2, 10, 10));

            add(new JLabel("Appointment ID:"));
            JTextField idField = new JTextField();
            add(idField);

            add(new JLabel("Date (dd-MM-yyyy):"));
            JTextField dateField = new JTextField();
            add(dateField);

            add(new JLabel("Time (HH:mm):"));
            JTextField timeField = new JTextField();
            add(timeField);

            add(new JLabel("Patient ID:"));
            JTextField patientField = new JTextField();
            add(patientField);

            add(new JLabel("Doctor ID:"));
            JTextField doctorField = new JTextField();
            add(doctorField);

            JButton addButton = new JButton("Add Appointment");
            addButton.addActionListener(e -> {
                try {
                    int appointmentID = Integer.parseInt(idField.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = sdf.parse(dateField.getText());
                    String time = timeField.getText();
                    int patientID = Integer.parseInt(patientField.getText());
                    int doctorID = Integer.parseInt(doctorField.getText());

                    Appointment appointment = new Appointment(appointmentID, date, time, patientID, doctorID);
                    appointment.schedule();
                    JOptionPane.showMessageDialog(this, "Appointment added successfully!");
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error adding appointment.");
                }
            });
            add(addButton);

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> dispose());
            add(cancelButton);

            setVisible(true);
        }
    }
