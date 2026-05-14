package pl.polsl.take.firmakurierska.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.take.firmakurierska.entity.Kurier;

public interface KurierRepository extends JpaRepository<Kurier, Long> {

}