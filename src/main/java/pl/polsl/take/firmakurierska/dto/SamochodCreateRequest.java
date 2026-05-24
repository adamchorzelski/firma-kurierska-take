package pl.polsl.take.firmakurierska.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SamochodCreateRequest {

    @NotBlank
    private String numerRejestracyjny;

    @NotBlank
    private String marka;

    @NotNull
    @Positive
    private Double ladownoscKg;
}
