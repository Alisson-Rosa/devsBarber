package project.devsbarber.repository;

import org.springframework.data.repository.CrudRepository;
import project.devsbarber.model.Role;

public interface RoleRepository extends CrudRepository <Role, Long> {
    Role findByRole(String role);
}