import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class AddScheduleWindow extends JFrame {
        public AddScheduleWindow() {
            setTitle("Add Schedule");
            setSize(400, 300);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(5, 2, 10, 10));

            add(new JLabel("Schedule ID:"));
            JTextField idField = new JTextField();
            add(idField);

            add(new JLabel("Doctor ID:"));
            JTextField doctorField = new JTextField();
            add(doctorField);

            add(new JLabel("Department ID:"));
            JTextField deptField = new JTextField();
            add(deptField);

            add(new JLabel("Day of Week:"));
            JTextField dayField = new JTextField();
            add(dayField);

            add(new JLabel("Start Time (HH:mm:ss):"));
            JTextField startTimeField = new JTextField();
            add(startTimeField);

            add(new JLabel("End Time (HH:mm:ss):"));
            JTextField endTimeField = new JTextField();
            add(endTimeField);

            JButton addButton = new JButton("Add Schedule");
            addButton.addActionListener(e -> {
                try {
                    int scheduleID = Integer.parseInt(idField.getText());
                    int doctorID = Integer.parseInt(doctorField.getText());
                    int deptID = Integer.parseInt(deptField.getText());
                    String day = dayField.getText();
                    Time startTime = Time.valueOf(startTimeField.getText());
                    Time endTime = Time.valueOf(endTimeField.getText());

                    Schedule schedule = new Schedule(scheduleID, doctorID, deptID, day, startTime, endTime);
                    schedule.createSchedule();
                    JOptionPane.showMessageDialog(this, "Schedule added successfully!");
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error adding schedule.");
                }
            });
            add(addButton);

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> dispose());
            add(cancelButton);

            setVisible(true);
        }
    }
