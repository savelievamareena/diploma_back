package by.iba.diploma_101_back.forms;

public class ReviewForm {
    private Integer doctorId;
    private String sender;
    private String reviewText;

    public Integer getDoctorId() {
        return doctorId;
    }

    public String getSender() {
        return sender;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
