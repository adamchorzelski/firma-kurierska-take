package pl.polsl.take.firmakurierska.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.polsl.take.firmakurierska.entity.Trasa;

public interface TrasaRepository extends JpaRepository<Trasa, Long> {

	java.util.List<Trasa> findByPrzypisanySamochod_Id(Long samochodId);

}