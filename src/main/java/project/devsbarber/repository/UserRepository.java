package project.devsbarber.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.User;

@Repository
@Transactional
public interface UserRepository extends CrudRepository <User, Long> {
    User findByUsername(String username);
}