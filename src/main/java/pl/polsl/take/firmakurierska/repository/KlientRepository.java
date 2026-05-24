package pl.polsl.take.firmakurierska.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.take.firmakurierska.entity.Klient;

import java.util.List;

public interface KlientRepository extends JpaRepository<Klient, Long> {
    List<Klient> findByNazwiskoIgnoreCase(String nazwisko);
}