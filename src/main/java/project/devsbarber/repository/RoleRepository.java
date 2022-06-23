package project.devsbarber.repository;

import org.springframework.data.repository.CrudRepository;
import project.devsbarber.model.entities.Role;

public interface RoleRepository extends CrudRepository <Role, Long> {
    Role getByName(String name);
}