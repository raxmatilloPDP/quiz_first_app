package uz.pdp.quiz_first_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationDTO {

    private String email;

    private String verificationCode;

}
