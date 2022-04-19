package project.devsbarber.repository;

import org.springframework.data.repository.CrudRepository;
import project.devsbarber.model.User;

public interface UserRepository extends CrudRepository <User, Long> {
    User findByUsername(String username);
}