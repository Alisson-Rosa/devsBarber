package project.devsbarber.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import project.devsbarber.model.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(String name);
}