package pl.polsl.take.firmakurierska.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trasa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataWyjazdu;
    private String nazwa;
    private String rejon;

    @ManyToOne
    @JoinColumn(name = "samochod_id")
    private Samochod przypisanySamochod;

    @ManyToOne
    @JoinColumn(name = "kurier_id")
    private Kurier kierowca;

    @OneToMany(mappedBy = "aktualnaTrasa")
    @JsonIgnore
    private List<Paczka> paczki = new ArrayList<>();
}