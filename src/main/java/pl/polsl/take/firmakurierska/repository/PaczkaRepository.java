package pl.polsl.take.firmakurierska.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.take.firmakurierska.entity.Paczka;

public interface PaczkaRepository extends JpaRepository<Paczka, Long> {

}