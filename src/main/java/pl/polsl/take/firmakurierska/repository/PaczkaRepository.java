package pl.polsl.take.firmakurierska.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.polsl.take.firmakurierska.entity.Paczka;
import pl.polsl.take.firmakurierska.entity.StatusPaczki;

public interface PaczkaRepository extends JpaRepository<Paczka, Long> {
    List<Paczka> findByStatus(StatusPaczki status);
    List<Paczka> findByNadawca_IdOrOdbiorca_Id(Long nadawcaId, Long odbiorcaId);
    List<Paczka> findByAktualnaTrasa_Id(Long trasaId);
}