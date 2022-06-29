package project.devsbarber.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.entities.Barber;

@Repository
@Transactional
public interface BarberRepository extends CrudRepository <Barber, Long> {

    Iterable<Barber> findAll();

    Barber getById(Long id);

    Barber findByName(String name);
}