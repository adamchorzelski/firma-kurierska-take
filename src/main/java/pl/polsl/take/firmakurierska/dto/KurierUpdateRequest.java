package pl.polsl.take.firmakurierska.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class KurierUpdateRequest {

    @NotBlank(message = "Imię jest wymagane")
    private String imie;

    @NotBlank(message = "Nazwisko jest wymagane")
    private String nazwisko;

    @NotBlank(message = "Numer pracownika jest wymagany")
    private String numerPracownika;
}
