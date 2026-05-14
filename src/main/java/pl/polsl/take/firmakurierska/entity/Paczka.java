package pl.polsl.take.firmakurierska.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paczka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numerNadania;
    private Double waga;
    private String adresDostarczenia;

    @Enumerated(EnumType.STRING)
    private StatusPaczki status;

    @ManyToOne
    @JoinColumn(name = "nadawca_id")
    private Klient nadawca;

    @ManyToOne
    @JoinColumn(name = "odbiorca_id")
    private Klient odbiorca;

    @ManyToOne
    @JoinColumn(name = "aktualna_trasa_id")
    private Trasa aktualnaTrasa;
}