package pl.polsl.take.firmakurierska.dto;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrasaModel extends RepresentationModel<TrasaModel> {
    private Long id;
    private LocalDate dataWyjazdu;
    private String nazwa;
    private String rejon;
    private Long przypisanySamochodId;
    private Long kierowcaId;
}
