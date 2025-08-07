import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class MedicalRecord {
    int recordID;
    int patientID;
    int doctorID;
    String details;

    private static final String FILE_NAME = "medical_records.csv";

    public MedicalRecord(int recordID, int patientID, int doctorID, String details) {
        this.recordID = recordID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.details = details;
    }

    public void addRecord() throws IOException {
        File file = new File(FILE_NAME);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        String data = recordID + "," + patientID + "," + doctorID + "," + details;
        writer.write(data);
        writer.newLine();
        writer.close();
    }

    public static MedicalRecord getRecordByID(int id) throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            if (Integer.parseInt(row[0]) == id) {
                reader.close();
                return new MedicalRecord(
                        Integer.parseInt(row[0]),
                        Integer.parseInt(row[1]),
                        Integer.parseInt(row[2]),
                        row[3]
                );
            }
        }
        reader.close();
        return null;
    }

    public static int generateUniqueRecordID() throws IOException {
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

    public static java.util.List<MedicalRecord> getRecordsByPatientID(int patientID) throws IOException {
        java.util.List<MedicalRecord> records = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return records;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 4) continue;
            if (Integer.parseInt(parts[1]) == patientID) {
                records.add(new MedicalRecord(
                        Integer.parseInt(parts[0]),
                        patientID,
                        Integer.parseInt(parts[2]),
                        parts[3]
                ));
            }
        }
        reader.close();
        return records;
    }

    public static java.util.List<MedicalRecord> loadAllMedicalRecords() throws IOException {
        java.util.List<MedicalRecord> records = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return records;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 4) continue;
            MedicalRecord record = new MedicalRecord(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    parts[3]
            );
            records.add(record);
        }
        reader.close();
        return records;
    }

    public int getRecordID() {
        return this.recordID;
    }

    public int getPatientID() {
        return this.patientID;
    }

    public int getDoctorID() {
        return this.doctorID;
    }

    public String getDetails() {
        return this.details;
    }
}
