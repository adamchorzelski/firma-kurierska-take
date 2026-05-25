package pl.polsl.take.firmakurierska.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaczkaCreateRequest {

    @NotBlank(message = "Numer nadania nie może być pusty")
    private String numerNadania;

    @NotNull(message = "Waga jest wymagana")
    private Double waga;

    @NotBlank(message = "Adres dostarczenia nie może być pusty")
    private String adresDostarczenia;

    @NotNull(message = "ID nadawcy jest wymagane")
    private Long nadawcaId;

    @NotNull(message = "ID odbiorcy jest wymagane")
    private Long odbiorcaId;
}