package pl.polsl.take.firmakurierska.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import pl.polsl.take.firmakurierska.dto.KlientCreateRequest;
import pl.polsl.take.firmakurierska.dto.KlientModel;
import pl.polsl.take.firmakurierska.dto.KlientUpdateRequest;
import pl.polsl.take.firmakurierska.entity.Klient;
import pl.polsl.take.firmakurierska.exception.ResourceNotFoundException;
import pl.polsl.take.firmakurierska.hateoas.KlientModelAssembler;
import pl.polsl.take.firmakurierska.repository.KlientRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/klienci")
@RequiredArgsConstructor
@Tag(name = "Klienci")
public class KlientController {

    private final KlientRepository klientRepository;
    private final KlientModelAssembler klientModelAssembler;

    @GetMapping
    @Operation(summary = "Pobierz wszystkich klientów", description = "Zwraca listę wszystkich klientów. Możliwe filtrowanie po nazwisku.")
    public CollectionModel<KlientModel> getAllKlienci(@RequestParam(required = false) String nazwisko) {
        List<Klient> entities;
        if (nazwisko != null && !nazwisko.isBlank()) {
            entities = klientRepository.findByNazwiskoIgnoreCase(nazwisko);
        } else {
            entities = klientRepository.findAll();
        }
        CollectionModel<KlientModel> collectionModel = klientModelAssembler.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(KlientController.class).getAllKlienci(nazwisko)).withSelfRel());
        return collectionModel;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz klienta po ID", description = "Zwraca klienta na podstawie przekazanego ID.")
    public KlientModel getKlientById(@PathVariable Long id) {
        Klient klient = klientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono klienta o ID: " + id));
        return klientModelAssembler.toModel(klient);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Dodaj nowego klienta", description = "Tworzy nowego klienta na podstawie podanych danych.")
    public KlientModel createKlient(@Valid @RequestBody KlientCreateRequest request) {
        Klient klient = new Klient();
        klient.setImie(request.getImie());
        klient.setNazwisko(request.getNazwisko());
        klient.setTelefon(request.getTelefon());
        klient.setAdres(request.getAdres());
        
        Klient saved = klientRepository.save(klient);
        return klientModelAssembler.toModel(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aktualizuj klienta", description = "Aktualizuje dane istniejącego klienta.")
    public KlientModel updateKlient(@PathVariable Long id, @Valid @RequestBody KlientUpdateRequest request) {
        Klient klient = klientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono klienta o ID: " + id));
        
        klient.setImie(request.getImie());
        klient.setNazwisko(request.getNazwisko());
        klient.setTelefon(request.getTelefon());
        klient.setAdres(request.getAdres());
        
        Klient saved = klientRepository.save(klient);
        return klientModelAssembler.toModel(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Usuń klienta", description = "Usuwa klienta z bazy danych.")
    public ResponseEntity<Void> deleteKlient(@PathVariable Long id) {
        Klient klient = klientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono klienta o ID: " + id));
        
        klientRepository.delete(klient);
        return ResponseEntity.noContent().build();
    }
}