package pl.polsl.take.firmakurierska.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pl.polsl.take.firmakurierska.dto.SamochodCreateRequest;
import pl.polsl.take.firmakurierska.dto.SamochodModel;
import pl.polsl.take.firmakurierska.dto.SamochodUpdateRequest;
import pl.polsl.take.firmakurierska.entity.Samochod;
import pl.polsl.take.firmakurierska.exception.ResourceNotFoundException;
import pl.polsl.take.firmakurierska.hateoas.SamochodModelAssembler;
import pl.polsl.take.firmakurierska.repository.SamochodRepository;

@RestController
@RequestMapping("/samochody")
public class SamochodController {

    private final SamochodRepository samochodRepository;
    private final SamochodModelAssembler assembler;

    public SamochodController(SamochodRepository samochodRepository, SamochodModelAssembler assembler) {
        this.samochodRepository = samochodRepository;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<SamochodModel> create(@Valid @RequestBody SamochodCreateRequest req) {
        Samochod s = new Samochod();
        s.setNumerRejestracyjny(req.getNumerRejestracyjny());
        s.setMarka(req.getMarka());
        s.setLadownoscKg(req.getLadownoscKg());

        Samochod saved = samochodRepository.save(s);
        SamochodModel model = assembler.toModel(saved);

        URI location = linkTo(methodOn(SamochodController.class).getById(saved.getId())).toUri();
        return ResponseEntity.created(location).body(model);
    }

    @GetMapping
    public CollectionModel<SamochodModel> getAll() {
        List<SamochodModel> models = samochodRepository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(models, linkTo(methodOn(SamochodController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public SamochodModel getById(@PathVariable Long id) {
        Samochod s = samochodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Samochod not found: " + id));
        return assembler.toModel(s);
    }

    @PutMapping("/{id}")
    public SamochodModel update(@PathVariable Long id, @Valid @RequestBody SamochodUpdateRequest req) {
        Samochod s = samochodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Samochod not found: " + id));

        s.setNumerRejestracyjny(req.getNumerRejestracyjny());
        s.setMarka(req.getMarka());
        s.setLadownoscKg(req.getLadownoscKg());

        Samochod saved = samochodRepository.save(s);
        return assembler.toModel(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Samochod s = samochodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Samochod not found: " + id));
        samochodRepository.delete(s);
        return ResponseEntity.noContent().build();
    }
}
