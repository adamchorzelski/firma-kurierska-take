package pl.polsl.take.firmakurierska.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import pl.polsl.take.firmakurierska.controller.PaczkaController;
import pl.polsl.take.firmakurierska.dto.PaczkaModel;
import pl.polsl.take.firmakurierska.entity.Paczka;

@Component
public class PaczkaModelAssembler implements RepresentationModelAssembler<Paczka, PaczkaModel> {

    @Override
    public PaczkaModel toModel(Paczka paczka) {
        PaczkaModel model = new PaczkaModel();
        model.setId(paczka.getId());
        model.setNumerNadania(paczka.getNumerNadania());
        model.setWaga(paczka.getWaga());
        model.setAdresDostarczenia(paczka.getAdresDostarczenia());
        model.setStatus(paczka.getStatus());
        if (paczka.getNadawca() != null) {
            model.setNadawcaId(paczka.getNadawca().getId());
        }
        if (paczka.getOdbiorca() != null) {
            model.setOdbiorcaId(paczka.getOdbiorca().getId());
        }
        if (paczka.getAktualnaTrasa() != null) {
            model.setAktualnaTrasaId(paczka.getAktualnaTrasa().getId());
        }
        model.add(linkTo(methodOn(PaczkaController.class).getPaczkaById(paczka.getId())).withSelfRel());
        model.add(linkTo(methodOn(PaczkaController.class).getAllPaczki(null)).withRel("paczki"));
        return model;
    }
}