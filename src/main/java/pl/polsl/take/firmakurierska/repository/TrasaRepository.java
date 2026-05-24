package pl.polsl.take.firmakurierska.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.polsl.take.firmakurierska.entity.Trasa;

public interface TrasaRepository extends JpaRepository<Trasa, Long> {

	List<Trasa> findByPrzypisanySamochod_Id(Long samochodId);

	List<Trasa> findByDataWyjazdu(LocalDate dataWyjazdu);

	List<Trasa> findByPrzypisanySamochod_IdAndDataWyjazdu(Long samochodId, LocalDate dataWyjazdu);

}