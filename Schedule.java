import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Time;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class Schedule {
    int scheduleID;
    int doctorID;
    int departmentID;
    String dayOfWeek;
    Time startTime;
    Time endTime;

    public static final String FILE_NAME = "schedules.csv";

    public Schedule(int scheduleID, int doctorID, int departmentID, String dayOfWeek, Time startTime, Time endTime) {
        this.scheduleID = scheduleID;
        this.doctorID = doctorID;
        this.departmentID = departmentID;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void createSchedule() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
        String data = scheduleID + "," + doctorID + "," + departmentID + "," + dayOfWeek + "," + startTime + "," + endTime;
        writer.write(data);
        writer.newLine();
        writer.close();
    }

    public void updateScheduleDetails() throws IOException {
        java.util.List<Schedule> schedules = loadSchedules();
        for (Schedule schedule : schedules) {
            if (schedule.scheduleID == this.scheduleID) {
                schedule.doctorID = this.doctorID;
                schedule.departmentID = this.departmentID;
                schedule.dayOfWeek = this.dayOfWeek;
                schedule.startTime = this.startTime;
                schedule.endTime = this.endTime;
            }
        }
        saveSchedules(schedules);
    }

    public String getAvailability() {
        return "Day: " + dayOfWeek + ", Start Time: " + startTime + ", End Time: " + endTime;
    }

    public int getDepartmentID() {
        return this.departmentID;
    }

    public int getDoctorID() {
        return this.doctorID;
    }

    public static int generateUniqueScheduleID() throws IOException {
        File file = new File(FILE_NAME);
        int maxID = 0;
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id > maxID) maxID = id;
            }
            reader.close();
        }
        return maxID + 1;
    }

    public static java.util.List<Schedule> loadSchedules() throws IOException {
        java.util.List<Schedule> schedules = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return schedules;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String lineContent;
        while ((lineContent = reader.readLine()) != null) {
            String[] parts = lineContent.split(",");
            schedules.add(new Schedule(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    parts[3],
                    Time.valueOf(parts[4]),
                    Time.valueOf(parts[5])
            ));
        }
        reader.close();
        return schedules;
    }

    public static java.util.List<Schedule> getSchedulesByDoctorID(int doctorID) throws IOException {
        java.util.List<Schedule> schedules = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return schedules;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String lineContent;
        while ((lineContent = reader.readLine()) != null) {
            String[] parts = lineContent.split(",");
            if (Integer.parseInt(parts[1]) == doctorID) {
                schedules.add(new Schedule(
                        Integer.parseInt(parts[0]),
                        doctorID,
                        Integer.parseInt(parts[2]),
                        parts[3],
                        Time.valueOf(parts[4]),
                        Time.valueOf(parts[5])
                ));
            }
        }
        reader.close();
        return schedules;
    }

    public static java.util.List<Schedule> getSchedulesByDepartmentID(int departmentID) throws IOException {
        java.util.List<Schedule> schedules = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return schedules;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String lineContent;
        while ((lineContent = reader.readLine()) != null) {
            String[] parts = lineContent.split(",");
            if (Integer.parseInt(parts[2]) == departmentID) {
                schedules.add(new Schedule(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        departmentID,
                        parts[3],
                        Time.valueOf(parts[4]),
                        Time.valueOf(parts[5])
                ));
            }
        }
        reader.close();
        return schedules;
    }

    private static void saveSchedules(java.util.List<Schedule> schedules) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
        for (Schedule schedule : schedules) {
            writer.write(schedule.scheduleID + "," + schedule.doctorID + "," + schedule.departmentID + "," + schedule.dayOfWeek + "," + schedule.startTime + "," + schedule.endTime);
            writer.newLine();
        }
        writer.close();
    }

    public static java.util.List<Schedule> loadAllSchedules() throws IOException {
        return loadSchedules();
    }

    public int getScheduleID() {
        return this.scheduleID;
    }

    public String getDayOfWeek() {
        return this.dayOfWeek;
    }

    public Time getStartTime() {
        return this.startTime;
    }

    public Time getEndTime() {
        return this.endTime;
    }
}
