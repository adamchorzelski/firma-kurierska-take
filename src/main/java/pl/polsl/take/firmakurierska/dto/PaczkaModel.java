package pl.polsl.take.firmakurierska.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;
import pl.polsl.take.firmakurierska.entity.StatusPaczki;

@Relation(collectionRelation = "paczki", itemRelation = "paczka")
@Getter
@Setter
public class PaczkaModel extends RepresentationModel<PaczkaModel> {
    private Long id;
    private String numerNadania;
    private Double waga;
    private String adresDostarczenia;
    private StatusPaczki status;
    private Long nadawcaId;
    private Long odbiorcaId;
    private Long aktualnaTrasaId;
}