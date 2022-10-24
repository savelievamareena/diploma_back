package by.iba.diploma_101_back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "review")
public class Review extends DataObject{

    @ManyToOne
    @JoinColumn(name="doctorId", referencedColumnName="id")
    private Doctor doctor;

    @Column(name="reviewText")
    private String reviewText;

    @Column(name="sender")
    private String sender;

    @Column(name="createdAt")
    protected String createdAt;

    public void setDoctor(Doctor doctor) {this.doctor = doctor;}

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setSender(String sender) {this.sender = sender;}

    public Doctor getDoctor() {
        return doctor;
    }

    public String getSender() {return sender;}

    public String getReviewText() {
        return reviewText;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
