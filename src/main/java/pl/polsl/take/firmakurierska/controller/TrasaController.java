package pl.polsl.take.firmakurierska.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.polsl.take.firmakurierska.entity.Trasa;
import pl.polsl.take.firmakurierska.repository.TrasaRepository;

@RestController
@RequestMapping("/trasy")
public class TrasaController {

    private final TrasaRepository trasaRepository;

    public TrasaController(TrasaRepository trasaRepository) {
        this.trasaRepository = trasaRepository;
    }

    @GetMapping
    public CollectionModel<EntityModel<Trasa>> getTrasy(@RequestParam(name = "samochodId", required = false) Long samochodId) {
        List<Trasa> list;
        if (samochodId == null) {
            list = trasaRepository.findAll();
        } else {
            list = trasaRepository.findByPrzypisanySamochod_Id(samochodId);
        }

        List<EntityModel<Trasa>> models = list.stream().map(t -> EntityModel.of(t,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TrasaController.class).getById(t.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(models, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TrasaController.class).getTrasy(samochodId)).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Trasa>> getById(@PathVariable Long id) {
        return trasaRepository.findById(id)
                .map(t -> ResponseEntity.ok(EntityModel.of(t,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TrasaController.class).getById(id)).withSelfRel())))
                .orElse(ResponseEntity.notFound().build());
    }
}
