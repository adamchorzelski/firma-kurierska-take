package pl.polsl.take.firmakurierska.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class KurierModel extends RepresentationModel<KurierModel> {

    private Long id;
    private String imie;
    private String nazwisko;
    private String numerPracownika;

}
