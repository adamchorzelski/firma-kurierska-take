package pl.polsl.take.firmakurierska.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.take.firmakurierska.entity.Kurier;
import java.util.List;

public interface KurierRepository extends JpaRepository<Kurier, Long> {

    List<Kurier> findByNazwiskoIgnoreCase(String nazwisko);

}