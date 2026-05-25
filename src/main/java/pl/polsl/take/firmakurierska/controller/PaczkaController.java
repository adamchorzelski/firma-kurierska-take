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
import pl.polsl.take.firmakurierska.dto.PaczkaCreateRequest;
import pl.polsl.take.firmakurierska.dto.PaczkaModel;
import pl.polsl.take.firmakurierska.dto.PaczkaUpdateRequest;
import pl.polsl.take.firmakurierska.entity.Klient;
import pl.polsl.take.firmakurierska.entity.Paczka;
import pl.polsl.take.firmakurierska.entity.StatusPaczki;
import pl.polsl.take.firmakurierska.entity.Trasa;
import pl.polsl.take.firmakurierska.exception.ResourceNotFoundException;
import pl.polsl.take.firmakurierska.hateoas.PaczkaModelAssembler;
import pl.polsl.take.firmakurierska.repository.KlientRepository;
import pl.polsl.take.firmakurierska.repository.PaczkaRepository;
import pl.polsl.take.firmakurierska.repository.TrasaRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/paczki")
@RequiredArgsConstructor
@Tag(name = "Paczki")
public class PaczkaController {

    private final PaczkaRepository paczkaRepository;
    private final KlientRepository klientRepository;
    private final TrasaRepository trasaRepository;
    private final PaczkaModelAssembler paczkaModelAssembler;

    @GetMapping
    @Operation(summary = "Pobierz wszystkie paczki", description = "Zwraca listę paczek. Możliwe filtrowanie po statusie.")
    public CollectionModel<PaczkaModel> getAllPaczki(@RequestParam(required = false) StatusPaczki status) {
        List<Paczka> entities;
        if (status != null) {
            entities = paczkaRepository.findByStatus(status);
        } else {
            entities = paczkaRepository.findAll();
        }
        CollectionModel<PaczkaModel> collectionModel = paczkaModelAssembler.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(PaczkaController.class).getAllPaczki(status)).withSelfRel());
        return collectionModel;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Pobierz paczkę po ID", description = "Zwraca paczkę na podstawie przekazanego ID.")
    public PaczkaModel getPaczkaById(@PathVariable Long id) {
        Paczka paczka = paczkaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono paczki o ID: " + id));
        return paczkaModelAssembler.toModel(paczka);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Dodaj nową paczkę", description = "Tworzy nową paczkę na podstawie podanych danych.")
    public PaczkaModel createPaczka(@Valid @RequestBody PaczkaCreateRequest request) {
        Klient nadawca = klientRepository.findById(request.getNadawcaId())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono klienta (nadawcy) o ID: " + request.getNadawcaId()));
        Klient odbiorca = klientRepository.findById(request.getOdbiorcaId())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono klienta (odbiorcy) o ID: " + request.getOdbiorcaId()));

        Paczka paczka = new Paczka();
        paczka.setNumerNadania(request.getNumerNadania());
        paczka.setWaga(request.getWaga());
        paczka.setAdresDostarczenia(request.getAdresDostarczenia());
        paczka.setNadawca(nadawca);
        paczka.setOdbiorca(odbiorca);
        paczka.setStatus(StatusPaczki.PRZYJETA_W_ODDZIALE);

        Paczka saved = paczkaRepository.save(paczka);
        return paczkaModelAssembler.toModel(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aktualizuj paczkę", description = "Aktualizuje dane istniejącej paczki.")
    public PaczkaModel updatePaczka(@PathVariable Long id, @Valid @RequestBody PaczkaUpdateRequest request) {
        Paczka paczka = paczkaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono paczki o ID: " + id));
        Klient nadawca = klientRepository.findById(request.getNadawcaId())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono klienta (nadawcy) o ID: " + request.getNadawcaId()));
        Klient odbiorca = klientRepository.findById(request.getOdbiorcaId())
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono klienta (odbiorcy) o ID: " + request.getOdbiorcaId()));

        paczka.setNumerNadania(request.getNumerNadania());
        paczka.setWaga(request.getWaga());
        paczka.setAdresDostarczenia(request.getAdresDostarczenia());
        paczka.setNadawca(nadawca);
        paczka.setOdbiorca(odbiorca);

        Paczka saved = paczkaRepository.save(paczka);
        return paczkaModelAssembler.toModel(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Usuń paczkę", description = "Usuwa paczkę z bazy danych.")
    public ResponseEntity<Void> deletePaczka(@PathVariable Long id) {
        Paczka paczka = paczkaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono paczki o ID: " + id));
        paczkaRepository.delete(paczka);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Zmień status paczki", description = "Zmienia status paczki, np. z W_TRASIE na DOSTARCZONA.")
    public PaczkaModel zmienStatus(@PathVariable Long id, @RequestParam StatusPaczki status) {
        Paczka paczka = paczkaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono paczki o ID: " + id));
        paczka.setStatus(status);
        Paczka saved = paczkaRepository.save(paczka);
        return paczkaModelAssembler.toModel(saved);
    }

    @PutMapping("/{id}/trasa")
    @Operation(summary = "Przypisz paczkę do trasy", description = "Przypisuje paczkę do konkretnej trasy.")
    public PaczkaModel przypiszTrase(@PathVariable Long id, @RequestParam Long trasaId) {
        Paczka paczka = paczkaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono paczki o ID: " + id));
        Trasa trasa = trasaRepository.findById(trasaId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono trasy o ID: " + trasaId));
        paczka.setAktualnaTrasa(trasa);
        Paczka saved = paczkaRepository.save(paczka);
        return paczkaModelAssembler.toModel(saved);
    }
}