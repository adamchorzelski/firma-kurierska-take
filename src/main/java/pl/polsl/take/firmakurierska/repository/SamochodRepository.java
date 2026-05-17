package pl.polsl.take.firmakurierska.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.take.firmakurierska.entity.Samochod;

public interface SamochodRepository extends JpaRepository<Samochod, Long> {

}