package by.iba.diploma_101_back.forms;

public class AppointmentForm {
    private int userId;
    private String beginning;
    private int scheduleId;
    private int serviceId;

    public int getUserId() {
        return userId;
    }

    public String getBeginning() {
        return beginning;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBeginning(String beginning) {
        this.beginning = beginning;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
