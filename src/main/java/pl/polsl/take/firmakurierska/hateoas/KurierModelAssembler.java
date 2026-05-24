package pl.polsl.take.firmakurierska.hateoas;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import pl.polsl.take.firmakurierska.controller.KurierController;
import pl.polsl.take.firmakurierska.dto.KurierModel;
import pl.polsl.take.firmakurierska.entity.Kurier;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class KurierModelAssembler extends RepresentationModelAssemblerSupport<Kurier, KurierModel> {

    public KurierModelAssembler() {
        super(KurierController.class, KurierModel.class);
    }

    @Override
    public KurierModel toModel(Kurier entity) {
        KurierModel model = KurierModel.builder()
                .id(entity.getId())
                .imie(entity.getImie())
                .nazwisko(entity.getNazwisko())
                .numerPracownika(entity.getNumerPracownika())
                .build();

        model.add(linkTo(methodOn(KurierController.class).getById(entity.getId())).withSelfRel());
        model.add(linkTo(methodOn(KurierController.class).getAll(null)).withRel("kurierzy"));

        return model;
    }
}
