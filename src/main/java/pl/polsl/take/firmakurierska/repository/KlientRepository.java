package pl.polsl.take.firmakurierska.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.take.firmakurierska.entity.Klient;

public interface KlientRepository extends JpaRepository<Klient, Long> {

}