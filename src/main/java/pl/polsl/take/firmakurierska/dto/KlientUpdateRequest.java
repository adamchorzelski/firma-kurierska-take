package pl.polsl.take.firmakurierska.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KlientUpdateRequest {
    @NotBlank(message = "Imię nie może być puste")
    private String imie;
    
    @NotBlank(message = "Nazwisko nie może być puste")
    private String nazwisko;
    
    @NotBlank(message = "Telefon nie może być pusty")
    private String telefon;
    
    @NotBlank(message = "Adres nie może być pusty")
    private String adres;
}