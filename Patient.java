import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class Patient {
    private int patientID;
    private String name;
    private Date dob;
    private String gender;
    private String contactInfo;
    private int departmentID;

    private static final String FILE_NAME = "patients.csv";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public Patient(int patientID, String name, Date dob, String gender, String contactInfo, int departmentID) {
        this.patientID = patientID;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.departmentID = departmentID;
    }

    public void register() throws IOException {
        File file = new File(FILE_NAME);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        String data = patientID + "," + name + "," + DATE_FORMAT.format(dob) + "," + gender + "," + contactInfo + "," + departmentID;
        writer.write(data);
        writer.newLine();
        writer.close();
    }

    public static Patient getPatientByID(int id) throws IOException, java.text.ParseException {
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            if (Integer.parseInt(row[0]) == id) {
                Date dob = DATE_FORMAT.parse(row[2]);
                int deptID = row.length > 5 ? Integer.parseInt(row[5]) : -1;
                reader.close();
                return new Patient(Integer.parseInt(row[0]), row[1], dob, row[3], row[4], deptID);
            }
        }
        reader.close();
        return null;
    }

    public Appointment bookAppointment(Date date, String time, int doctorID) throws IOException, java.text.ParseException {
        int appointmentID = Appointment.generateUniqueAppointmentID();
        Appointment appointment = new Appointment(appointmentID, date, time, this.patientID, doctorID);
        appointment.schedule();
        return appointment;
    }

    public MedicalRecord addMedicalRecord(String details) throws IOException {
        int recordID = MedicalRecord.generateUniqueRecordID();
        MedicalRecord record = new MedicalRecord(recordID, this.patientID, -1, details);
        record.addRecord();
        return record;
    }

    public java.util.List<MedicalRecord> getMedicalRecords() throws IOException {
        return MedicalRecord.getRecordsByPatientID(this.patientID);
    }

    public int getDepartmentID() {
        return this.departmentID;
    }

    public void setDepartmentID(int departmentID) throws IOException {
        Department department = Department.getDepartmentByID(departmentID);
        if (department == null) {
            throw new IllegalArgumentException("Department ID does not exist.");
        }
        this.departmentID = departmentID;
        updatePatientDepartmentInCSV();
    }

    private void updatePatientDepartmentInCSV() throws IOException {
        java.util.List<String> lines = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (Integer.parseInt(parts[0]) == this.patientID) {
                line = String.join(",", Arrays.copyOf(parts, 5)) + "," + this.departmentID;
            }
            lines.add(line);
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (String lineContent : lines) {
            writer.write(lineContent);
            writer.newLine();
        }
        writer.close();
    }

    public int getPatientID() {
        return this.patientID;
    }

    public String getName() {
        return this.name;
    }

    public Date getDob() {
        return this.dob;
    }

    public String getGender() {
        return this.gender;
    }

    public String getContactInfo() {
        return this.contactInfo;
    }

    public static java.util.List<Patient> loadAllPatients() throws IOException, java.text.ParseException {
        java.util.List<Patient> patients = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return patients;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String lineContent;
        while ((lineContent = reader.readLine()) != null) {
            String[] parts = lineContent.split(",");
            Date dob = DATE_FORMAT.parse(parts[2]);
            int deptID = parts.length > 5 ? Integer.parseInt(parts[5]) : -1;
            patients.add(new Patient(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    dob,
                    parts[3],
                    parts[4],
                    deptID
            ));
        }
        reader.close();
        return patients;
    }
}