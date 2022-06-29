package project.devsbarber.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.entities.TimeKey;

@Repository
@Transactional
public interface TimeKeyRepository extends CrudRepository<TimeKey, Long> {
}
