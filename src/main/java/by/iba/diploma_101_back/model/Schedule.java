package by.iba.diploma_101_back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "schedule")
public class Schedule extends DataObject{
    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    @Column(name = "scheduleDate")
    private String scheduleDate;

    @Column(name = "startTime")
    private String startTime;

    @Column(name = "endTime")
    private String endTime;

    public Doctor getDoctor() {
        return doctor;
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

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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
