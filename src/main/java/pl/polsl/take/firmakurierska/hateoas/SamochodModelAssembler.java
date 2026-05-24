package pl.polsl.take.firmakurierska.hateoas;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import pl.polsl.take.firmakurierska.controller.SamochodController;
import pl.polsl.take.firmakurierska.controller.TrasaController;
import pl.polsl.take.firmakurierska.dto.SamochodModel;
import pl.polsl.take.firmakurierska.entity.Samochod;

@Component
public class SamochodModelAssembler implements RepresentationModelAssembler<Samochod, SamochodModel> {

    @Override
    public SamochodModel toModel(Samochod entity) {
        SamochodModel model = new SamochodModel(entity.getId(), entity.getNumerRejestracyjny(), entity.getMarka(), entity.getLadownoscKg());

        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SamochodController.class).getById(entity.getId())).withSelfRel());
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SamochodController.class).getAll()).withRel("samochody"));
        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TrasaController.class).getTrasy(entity.getId())).withRel("trasy"));

        return model;
    }
}
