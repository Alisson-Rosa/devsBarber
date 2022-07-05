package project.devsbarber.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.entities.Barber;

@Repository
@Transactional
public interface BarberRepository extends JpaRepository<Barber, Long> {

    Barber getById(Long id);

    Barber findByName(String name);
}