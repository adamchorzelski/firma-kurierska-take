package pl.polsl.take.firmakurierska.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.take.firmakurierska.dto.KurierCreateRequest;
import pl.polsl.take.firmakurierska.dto.KurierModel;
import pl.polsl.take.firmakurierska.dto.KurierUpdateRequest;
import pl.polsl.take.firmakurierska.entity.Kurier;
import pl.polsl.take.firmakurierska.exception.ResourceNotFoundException;
import pl.polsl.take.firmakurierska.hateoas.KurierModelAssembler;
import pl.polsl.take.firmakurierska.repository.KurierRepository;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/kurierzy")
@RequiredArgsConstructor
@Tag(name = "Kurierzy")
public class KurierController {

    private final KurierRepository kurierRepository;
    private final KurierModelAssembler kurierModelAssembler;

    @GetMapping
    @Operation(summary = "Pobierz wszystkich kurierów", description = "Zwraca listę wszystkich kurierów. Można filtrować po nazwisku.")
    public ResponseEntity<CollectionModel<KurierModel>> getAll(
            @RequestParam(required = false) String nazwisko) {

        List<Kurier> kurierzy;
        if (nazwisko != null && !nazwisko.isBlank()) {
            kurierzy = kurierRepository.findByNazwiskoIgnoreCase(nazwisko);
        } else {
            kurierzy = kurierRepository.findAll();
        }

        CollectionModel<KurierModel> collectionModel = kurierModelAssembler.toCollectionModel(kurierzy);
        collectionModel.add(linkTo(methodOn(KurierController.class).getAll(nazwisko)).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz kuriera", description = "Zwraca dane kuriera o podanym ID wraz z linkami HATEOAS.")
    public ResponseEntity<KurierModel> getById(@PathVariable Long id) {
        Kurier kurier = kurierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kurier o ID " + id + " nie istnieje"));
                
        return ResponseEntity.ok(kurierModelAssembler.toModel(kurier));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Dodaj kuriera", description = "Tworzy nowego kuriera")
    public ResponseEntity<KurierModel> create(@Valid @RequestBody KurierCreateRequest request) {
        Kurier kurier = new Kurier();
        kurier.setImie(request.getImie());
        kurier.setNazwisko(request.getNazwisko());
        kurier.setNumerPracownika(request.getNumerPracownika());

        Kurier saved = kurierRepository.save(kurier);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(kurierModelAssembler.toModel(saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Zaktualizuj kuriera", description = "Aktualizuje dane istniejącego kuriera")
    public ResponseEntity<KurierModel> update(
            @PathVariable Long id, 
            @Valid @RequestBody KurierUpdateRequest request) {
            
        Kurier kurier = kurierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kurier o ID " + id + " nie istnieje"));

        kurier.setImie(request.getImie());
        kurier.setNazwisko(request.getNazwisko());
        kurier.setNumerPracownika(request.getNumerPracownika());

        Kurier saved = kurierRepository.save(kurier);
        return ResponseEntity.ok(kurierModelAssembler.toModel(saved));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Usuń kuriera", description = "Usuwa kuriera o podanym ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Kurier kurier = kurierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kurier o ID " + id + " nie istnieje"));
                
        kurierRepository.delete(kurier);
        return ResponseEntity.noContent().build();
    }
}
