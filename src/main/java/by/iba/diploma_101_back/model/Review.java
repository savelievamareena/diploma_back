package by.iba.diploma_101_back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue
    @Column
    private int id;

    @Column(name="doctorId")
    private Integer doctorId;

    @Column(name="reviewText")
    private String reviewText;

    @Column(name="sender")
    private String sender;

    @Column(nullable = false, updatable = false, name="createdAt")
    @CreatedDate
    private String createdAt;

    public Review(Integer doctorId, String reviewText, String sender, String createdAt) {
        this.doctorId = doctorId;
        this.reviewText = reviewText;
        this.sender = sender;
        this.createdAt = createdAt;
    }

    public int getId() {return id;}

    public void setDoctorId(Integer doctorId) {this.doctorId = doctorId;}

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setSender(String sender) {this.sender = sender;}

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public Integer getDoctorId() {
        return doctorId;
    }

    public String getSender() {return sender;}

    public String getReviewText() {
        return reviewText;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
