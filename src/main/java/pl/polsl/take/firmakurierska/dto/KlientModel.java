package pl.polsl.take.firmakurierska.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "klienci", itemRelation = "klient")
@Getter
@Setter
public class KlientModel extends RepresentationModel<KlientModel> {
    private Long id;
    private String imie;
    private String nazwisko;
    private String telefon;
    private String adres;
}