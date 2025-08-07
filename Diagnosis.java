import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class Diagnosis {
    int diagnosisID;
    int patientID;
    int doctorID;
    String details;
    java.util.List<Integer> medicationIDs;

    private static final String FILE_NAME = "diagnoses.csv";

    public Diagnosis(int diagnosisID, int patientID, int doctorID, String details, java.util.List<Integer> medicationIDs) {
        this.diagnosisID = diagnosisID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.details = details;
        this.medicationIDs = medicationIDs;
    }

    public void addDiagnosis() throws IOException {
        File file = new File(FILE_NAME);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        String meds = String.join("|", medicationIDs.stream().map(String::valueOf).toArray(String[]::new));
        String data = diagnosisID + "," + patientID + "," + doctorID + "," + details + "," + meds;
        writer.write(data);
        writer.newLine();
        writer.close();
    }

    public static Diagnosis getDiagnosisByID(int id) throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) return null;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(",");
            if (Integer.parseInt(row[0]) == id) {
                java.util.List<Integer> meds = new ArrayList<>();
                if (row.length >= 5 && !row[4].isEmpty()) {
                    String[] medParts = row[4].split("\\|");
                    for (String med : medParts) {
                        meds.add(Integer.parseInt(med));
                    }
                }
                reader.close();
                return new Diagnosis(
                        Integer.parseInt(row[0]),
                        Integer.parseInt(row[1]),
                        Integer.parseInt(row[2]),
                        row[3],
                        meds
                );
            }
        }
        reader.close();
        return null;
    }

    public void addMedication(int medicationID) throws IOException {
        if (!medicationIDs.contains(medicationID)) {
            medicationIDs.add(medicationID);
            updateDiagnosisInCSV();
        }
    }

    public java.util.List<Medication> getMedications() throws IOException {
        java.util.List<Medication> medications = new ArrayList<>();
        for (int medID : medicationIDs) {
            Medication med = Medication.getMedicationByID(medID);
            if (med != null) {
                medications.add(med);
            }
        }
        return medications;
    }

    private void updateDiagnosisInCSV() throws IOException {
        java.util.List<String> lines = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String lineContent;
        String updatedLine = "";
        while ((lineContent = reader.readLine()) != null) {
            String[] parts = lineContent.split(",");
            if (Integer.parseInt(parts[0]) == this.diagnosisID) {
                String meds = String.join("|", medicationIDs.stream().map(String::valueOf).toArray(String[]::new));
                updatedLine = diagnosisID + "," + patientID + "," + doctorID + "," + details + "," + meds;
                lines.add(updatedLine);
            } else {
                lines.add(lineContent);
            }
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (String lineToWrite : lines) {
            writer.write(lineToWrite);
            writer.newLine();
        }
        writer.close();
    }

    public static int generateUniqueDiagnosisID() throws IOException {
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

    public static java.util.List<Diagnosis> loadAllDiagnoses() throws IOException {
        java.util.List<Diagnosis> diagnoses = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return diagnoses;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 4) continue;
            int diagnosisID = Integer.parseInt(parts[0]);
            int patientID = Integer.parseInt(parts[1]);
            int doctorID = Integer.parseInt(parts[2]);
            String details = parts[3];
            java.util.List<Integer> medicationIDs = new ArrayList<>();
            if (parts.length >= 5 && !parts[4].isEmpty()) {
                String[] meds = parts[4].split("\\|");
                for (String med : meds) {
                    medicationIDs.add(Integer.parseInt(med.trim()));
                }
            }
            diagnoses.add(new Diagnosis(diagnosisID, patientID, doctorID, details, medicationIDs));
        }
        reader.close();
        return diagnoses;
    }

    public int getDiagnosisID() {
        return this.diagnosisID;
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

    public java.util.List<Integer> getMedicationIDs() {
        return this.medicationIDs;
    }
}
