package by.iba.diploma_101_back.forms;

public class ReportForm {
    private int id;
    private String reportText;
    private int appointmentId;

    public int getId() {
        return id;
    }

    public String getReportText() {
        return reportText;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
}
