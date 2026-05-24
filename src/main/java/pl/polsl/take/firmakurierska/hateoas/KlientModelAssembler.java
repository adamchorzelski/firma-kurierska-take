package pl.polsl.take.firmakurierska.hateoas;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import pl.polsl.take.firmakurierska.controller.KlientController;
import pl.polsl.take.firmakurierska.dto.KlientModel;
import pl.polsl.take.firmakurierska.entity.Klient;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class KlientModelAssembler extends RepresentationModelAssemblerSupport<Klient, KlientModel> {

    public KlientModelAssembler() {
        super(KlientController.class, KlientModel.class);
    }

    @Override
    public KlientModel toModel(Klient entity) {
        KlientModel model = instantiateModel(entity);
        model.setId(entity.getId());
        model.setImie(entity.getImie());
        model.setNazwisko(entity.getNazwisko());
        model.setTelefon(entity.getTelefon());
        model.setAdres(entity.getAdres());
        
        model.add(linkTo(methodOn(KlientController.class).getKlientById(entity.getId())).withSelfRel());
        model.add(linkTo(methodOn(KlientController.class).getAllKlienci(null)).withRel("klienci"));
        
        return model;
    }
}