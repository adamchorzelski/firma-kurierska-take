package pl.polsl.take.firmakurierska.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import pl.polsl.take.firmakurierska.controller.SamochodController;
import pl.polsl.take.firmakurierska.controller.TrasaController;
import pl.polsl.take.firmakurierska.dto.TrasaModel;
import pl.polsl.take.firmakurierska.entity.Trasa;

@Component
public class TrasaModelAssembler implements RepresentationModelAssembler<Trasa, TrasaModel> {

    @Override
    public TrasaModel toModel(Trasa entity) {
        Long samochodId = entity.getPrzypisanySamochod() != null ? entity.getPrzypisanySamochod().getId() : null;
        Long kierowcaId = entity.getKierowca() != null ? entity.getKierowca().getId() : null;

        TrasaModel model = new TrasaModel(
                entity.getId(),
                entity.getDataWyjazdu(),
                entity.getNazwa(),
                entity.getRejon(),
                samochodId,
                kierowcaId);

        model.add(linkTo(methodOn(TrasaController.class).getById(entity.getId())).withSelfRel());
        model.add(linkTo(methodOn(TrasaController.class).getTrasy(null, null, null)).withRel("trasy"));

        if (samochodId != null) {
            model.add(linkTo(methodOn(SamochodController.class).getById(samochodId)).withRel("samochod"));
            model.add(linkTo(methodOn(TrasaController.class).getTrasy(samochodId, null, null)).withRel("trasySamochodu"));
        }

        return model;
    }
}
