package pl.polsl.take.firmakurierska.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SamochodModel extends RepresentationModel<SamochodModel> {
    private Long id;
    private String numerRejestracyjny;
    private String marka;
    private Double ladownoscKg;
}
