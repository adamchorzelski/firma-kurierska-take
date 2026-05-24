package pl.polsl.take.firmakurierska.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrasaUpdateRequest {

    @NotNull
    private LocalDate dataWyjazdu;

    @NotBlank
    private String nazwa;

    @NotBlank
    private String rejon;

    private Long przypisanySamochodId;
    private Long kierowcaId;
}
