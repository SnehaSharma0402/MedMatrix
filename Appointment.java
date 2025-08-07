import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class Appointment {
    int appointmentID;
    Date date;
    String time;
    int patientID;
    int doctorID;

    public static final String FILE_NAME = "appointments.csv";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public Appointment(int appointmentID, Date date, String time, int patientID, int doctorID) {
        this.appointmentID = appointmentID;
        this.date = date;
        this.time = time;
        this.patientID = patientID;
        this.doctorID = doctorID;
    }

    public void schedule() throws IOException {
        File file = new File(FILE_NAME);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        String data = appointmentID + "," + DATE_FORMAT.format(date) + "," + time + "," + patientID + "," + doctorID;
        writer.write(data);
        writer.newLine();
        writer.close();
    }

    public static Appointment getAppointmentByID(int id) throws IOException, java.text.ParseException {
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            if (Integer.parseInt(row[0]) == id) {
                Date date = DATE_FORMAT.parse(row[1]);
                reader.close();
                return new Appointment(Integer.parseInt(row[0]), date, row[2], Integer.parseInt(row[3]), Integer.parseInt(row[4]));
            }
        }
        reader.close();
        return null;
    }

    public static int generateUniqueAppointmentID() throws IOException {
        File file = new File(FILE_NAME);
        int maxID = 0;
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 0) continue;
                int id = Integer.parseInt(parts[0]);
                if (id > maxID) maxID = id;
            }
            reader.close();
        }
        return maxID + 1;
    }

    public static java.util.List<Appointment> getAppointmentsByDoctorID(int doctorID) throws IOException, java.text.ParseException {
        java.util.List<Appointment> appointments = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return appointments;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 5) continue;
            if (Integer.parseInt(parts[4]) == doctorID) {
                Date date = DATE_FORMAT.parse(parts[1]);
                appointments.add(new Appointment(
                        Integer.parseInt(parts[0]),
                        date,
                        parts[2],
                        Integer.parseInt(parts[3]),
                        doctorID
                ));
            }
        }
        reader.close();
        return appointments;
    }

    public static java.util.List<Appointment> loadAllAppointments() throws IOException, java.text.ParseException {
        java.util.List<Appointment> appointments = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return appointments;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 5) continue;
            Date date = DATE_FORMAT.parse(parts[1]);
            Appointment appointment = new Appointment(
                    Integer.parseInt(parts[0]),
                    date,
                    parts[2],
                    Integer.parseInt(parts[3]),
                    Integer.parseInt(parts[4])
            );
            appointments.add(appointment);
        }
        reader.close();
        return appointments;
    }

    public int getAppointmentID() {
        return this.appointmentID;
    }

    public Date getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public int getPatientID() {
        return this.patientID;
    }

    public int getDoctorID() {
        return this.doctorID;
    }
}
