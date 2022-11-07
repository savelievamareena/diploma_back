package by.iba.diploma_101_back.forms;

public class ScheduleForm {
    int id = 0;
    int doctorId = 0;
    String scheduleDate = "";
    String startTime = "";
    String endTime = "";

    public int getId() {
        return id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
