import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class Doctor {
    int doctorID;
    String name;
    String specialization;
    String contactInfo;
    private int departmentID;

    private static final String FILE_NAME = "doctors.csv";

    public Doctor(int doctorID, String name, String specialization, String contactInfo, int departmentID) {
        this.doctorID = doctorID;
        this.name = name;
        this.specialization = specialization;
        this.contactInfo = contactInfo;
        this.departmentID = departmentID;
    }

    public void addDoctor() throws IOException {
        File file = new File(FILE_NAME);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        String data = doctorID + "," + name + "," + specialization + "," + contactInfo + "," + departmentID;
        writer.write(data);
        writer.newLine();
        writer.close();
    }

    public static Doctor getDoctorByID(int id) throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            if (Integer.parseInt(row[0]) == id) {
                int deptID = row.length > 4 ? Integer.parseInt(row[4]) : -1;
                reader.close();
                return new Doctor(Integer.parseInt(row[0]), row[1], row[2], row[3], deptID);
            }
        }
        reader.close();
        return null;
    }

    public java.util.List<Appointment> getAppointments() throws IOException, java.text.ParseException {
        return Appointment.getAppointmentsByDoctorID(this.doctorID);
    }

    public MedicalRecord createMedicalRecord(int patientID, String details) throws IOException {
        int recordID = MedicalRecord.generateUniqueRecordID();
        MedicalRecord record = new MedicalRecord(recordID, patientID, this.doctorID, details);
        record.addRecord();
        return record;
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
        updateDoctorDepartmentInCSV();
    }

    private void updateDoctorDepartmentInCSV() throws IOException {
        java.util.List<String> lines = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (Integer.parseInt(parts[0]) == this.doctorID) {
                line = String.join(",", Arrays.copyOf(parts, 4)) + "," + this.departmentID;
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

    public Schedule createSchedule(int departmentID, String dayOfWeek, Time startTime, Time endTime) throws IOException {
        int scheduleID = Schedule.generateUniqueScheduleID();
        Schedule schedule = new Schedule(scheduleID, this.doctorID, departmentID, dayOfWeek, startTime, endTime);
        schedule.createSchedule();
        return schedule;
    }

    public java.util.List<Schedule> getSchedules() throws IOException {
        return Schedule.getSchedulesByDoctorID(this.doctorID);
    }

    public int getDoctorID() {
        return this.doctorID;
    }

    public String getName() {
        return this.name;
    }

    public String getSpecialization() {
        return this.specialization;
    }

    public String getContactInfo() {
        return this.contactInfo;
    }

    public static java.util.List<Doctor> loadAllDoctors() throws IOException {
        java.util.List<Doctor> doctors = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return doctors;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String lineContent;
        while ((lineContent = reader.readLine()) != null) {
            String[] parts = lineContent.split(",");
            if (parts.length < 5) continue;
            int deptID = Integer.parseInt(parts[4]);
            doctors.add(new Doctor(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    parts[2],
                    parts[3],
                    deptID
            ));
        }
        reader.close();
        return doctors;
    }
}