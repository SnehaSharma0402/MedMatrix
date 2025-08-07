import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Medical Management System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10)); 
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);

        String[] buttonLabels = {
            "Add Patient", "View Patients", 
            "Add Doctor", "View Doctors", 
            "Add Appointment", "View Appointments", 
            "Add Schedule", "View Schedules", 
            "Add Department", "View Departments", 
            "Add Inventory Manager", "View Inventory Managers", 
            "Add Inventory Item", "View Inventory Items", 
            "Add Supplier", "View Suppliers"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this::handleButtonAction);
            panel.add(button);
        }

        setVisible(true);
    }

    private void handleButtonAction(ActionEvent e) {
        String action = ((JButton) e.getSource()).getText();
        switch (action) {
            case "Add Patient": new AddPatientWindow(); break;
            case "View Patients": new ViewPatientsWindow(); break;
            case "Add Doctor": new AddDoctorWindow(); break;
            case "View Doctors": new ViewDoctorsWindow(); break;
            case "Add Appointment": new AddAppointmentWindow(); break;
            case "View Appointments": new ViewAppointmentsWindow(); break;
            case "Add Schedule": new AddScheduleWindow(); break;
            case "View Schedules": new ViewSchedulesWindow(); break;
            case "Add Department": new AddDepartmentWindow(); break;
            case "View Departments": new ViewDepartmentsWindow(); break;
            case "Add Inventory Manager": new AddInventoryManagerWindow(); break;
            case "View Inventory Managers": new ViewInventoryManagersWindow(); break;
            case "Add Inventory Item": new AddInventoryItemWindow(); break;
            case "View Inventory Items": new ViewInventoryItemsWindow(); break;
            case "Add Supplier": new AddSupplierWindow(); break;
            case "View Suppliers": new ViewSuppliersWindow(); break;
        }
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
