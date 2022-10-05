package by.iba.diploma_101_back.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewForm {
    private int id;
    private int doctorId;
    private String reviewText;
    private String createdAt;
}
