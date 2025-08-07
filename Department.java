import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class Department {
    int departmentID;
    String name;
    String description;

    private static final String FILE_NAME = "departments.csv";

    public Department(int departmentID, String name, String description) {
        this.departmentID = departmentID;
        this.name = name;
        this.description = description;
    }

    public void addDepartment() throws IOException {
        File file = new File(FILE_NAME);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        String data = departmentID + "," + name + "," + description;
        writer.write(data);
        writer.newLine();
        writer.close();
    }

    public static Department getDepartmentByID(int id) throws IOException {
        File file = new File(FILE_NAME);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            if (Integer.parseInt(row[0]) == id) {
                return new Department(Integer.parseInt(row[0]), row[1], row[2]);
            }
        }
        reader.close();
        return null;
    }

    public java.util.List<Doctor> getDoctors() throws IOException {
        java.util.List<Doctor> allDoctors = Doctor.loadAllDoctors();
        java.util.List<Doctor> deptDoctors = new ArrayList<>();
        for (Doctor doc : allDoctors) {
            if (doc.getDepartmentID() == this.departmentID) {
                deptDoctors.add(doc);
            }
        }
        return deptDoctors;
    }

    public java.util.List<Patient> getPatients() throws IOException, java.text.ParseException {
        java.util.List<Patient> allPatients = Patient.loadAllPatients();
        java.util.List<Patient> deptPatients = new ArrayList<>();
        for (Patient pat : allPatients) {
            if (pat.getDepartmentID() == this.departmentID) {
                deptPatients.add(pat);
            }
        }
        return deptPatients;
    }

    public java.util.List<Schedule> getDepartmentSchedules() throws IOException {
        return Schedule.getSchedulesByDepartmentID(this.departmentID);
    }

    public void addPatientToDepartment(int patientID) throws IOException, java.text.ParseException {
        Patient patient = Patient.getPatientByID(patientID);
        if (patient != null) {
            patient.setDepartmentID(this.departmentID);
        }
    }

    public void addDoctorToDepartment(int doctorID) throws IOException {
        Doctor doctor = Doctor.getDoctorByID(doctorID);
        if (doctor != null) {
            doctor.setDepartmentID(this.departmentID);
        }
    }

    public int getDepartmentID() {
        return this.departmentID;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public static java.util.List<Department> loadDepartments() throws IOException {
        java.util.List<Department> departments = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return departments;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String lineContent;
        while ((lineContent = reader.readLine()) != null) {
            String[] parts = lineContent.split(",");
            departments.add(new Department(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    parts[2]
            ));
        }
        reader.close();
        return departments;
    }
}