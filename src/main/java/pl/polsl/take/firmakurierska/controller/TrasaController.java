package pl.polsl.take.firmakurierska.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pl.polsl.take.firmakurierska.dto.TrasaCreateRequest;
import pl.polsl.take.firmakurierska.dto.TrasaModel;
import pl.polsl.take.firmakurierska.dto.TrasaUpdateRequest;
import pl.polsl.take.firmakurierska.entity.Kurier;
import pl.polsl.take.firmakurierska.entity.Samochod;
import pl.polsl.take.firmakurierska.entity.Trasa;
import pl.polsl.take.firmakurierska.exception.ResourceNotFoundException;
import pl.polsl.take.firmakurierska.hateoas.TrasaModelAssembler;
import pl.polsl.take.firmakurierska.repository.KurierRepository;
import pl.polsl.take.firmakurierska.repository.SamochodRepository;
import pl.polsl.take.firmakurierska.repository.TrasaRepository;

@RestController
@RequestMapping("/trasy")
public class TrasaController {

    private final TrasaRepository trasaRepository;
    private final SamochodRepository samochodRepository;
    private final KurierRepository kurierRepository;
    private final TrasaModelAssembler assembler;

    public TrasaController(
            TrasaRepository trasaRepository,
            SamochodRepository samochodRepository,
            KurierRepository kurierRepository,
            TrasaModelAssembler assembler) {
        this.trasaRepository = trasaRepository;
        this.samochodRepository = samochodRepository;
        this.kurierRepository = kurierRepository;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<TrasaModel> getTrasy(
            @RequestParam(name = "samochodId", required = false) Long samochodId,
            @RequestParam(name = "data", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        List<Trasa> list;

        if (samochodId != null && data != null) {
            list = trasaRepository.findByPrzypisanySamochod_IdAndDataWyjazdu(samochodId, data);
        } else if (samochodId != null) {
            list = trasaRepository.findByPrzypisanySamochod_Id(samochodId);
        } else if (data != null) {
            list = trasaRepository.findByDataWyjazdu(data);
        } else {
            list = trasaRepository.findAll();
        }

        List<TrasaModel> models = list.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(models, linkTo(methodOn(TrasaController.class).getTrasy(samochodId, data)).withSelfRel());
    }

    @GetMapping("/{id}")
    public TrasaModel getById(@PathVariable Long id) {
        Trasa trasa = trasaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trasa not found: " + id));
        return assembler.toModel(trasa);
    }

    @PostMapping
    public ResponseEntity<TrasaModel> create(@Valid @RequestBody TrasaCreateRequest req) {
        Trasa trasa = new Trasa();
        trasa.setDataWyjazdu(req.getDataWyjazdu());
        trasa.setNazwa(req.getNazwa());
        trasa.setRejon(req.getRejon());
        trasa.setPrzypisanySamochod(resolveSamochod(req.getPrzypisanySamochodId()));
        trasa.setKierowca(resolveKurier(req.getKierowcaId()));

        Trasa saved = trasaRepository.save(trasa);
        TrasaModel model = assembler.toModel(saved);

        URI location = linkTo(methodOn(TrasaController.class).getById(saved.getId())).toUri();
        return ResponseEntity.created(location).body(model);
    }

    @PutMapping("/{id}")
    public TrasaModel update(@PathVariable Long id, @Valid @RequestBody TrasaUpdateRequest req) {
        Trasa trasa = trasaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trasa not found: " + id));

        trasa.setDataWyjazdu(req.getDataWyjazdu());
        trasa.setNazwa(req.getNazwa());
        trasa.setRejon(req.getRejon());
        trasa.setPrzypisanySamochod(resolveSamochod(req.getPrzypisanySamochodId()));
        trasa.setKierowca(resolveKurier(req.getKierowcaId()));

        Trasa saved = trasaRepository.save(trasa);
        return assembler.toModel(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Trasa trasa = trasaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trasa not found: " + id));
        trasaRepository.delete(trasa);
        return ResponseEntity.noContent().build();
    }

    private Samochod resolveSamochod(Long samochodId) {
        if (samochodId == null) {
            return null;
        }

        return samochodRepository.findById(samochodId)
                .orElseThrow(() -> new ResourceNotFoundException("Samochod not found: " + samochodId));
    }

    private Kurier resolveKurier(Long kurierId) {
        if (kurierId == null) {
            return null;
        }

        return kurierRepository.findById(kurierId)
                .orElseThrow(() -> new ResourceNotFoundException("Kurier not found: " + kurierId));
    }
}
