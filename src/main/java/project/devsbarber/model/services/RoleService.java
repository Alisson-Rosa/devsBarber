package project.devsbarber.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.devsbarber.model.entities.Role;
import project.devsbarber.model.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    @Autowired private RoleRepository roleRepository;


    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role get(Long id) {
        return roleRepository.getById(id);
    }
}
