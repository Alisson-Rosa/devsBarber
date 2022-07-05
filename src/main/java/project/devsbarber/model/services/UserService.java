package project.devsbarber.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.devsbarber.model.entities.Role;
import project.devsbarber.model.entities.User;
import project.devsbarber.model.repository.UserRepository;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUserLogado() {

        Object userLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String nome;
        if (userLogado instanceof UserDetails) {
            nome = ((UserDetails)userLogado).getUsername();
        } else {
            nome = userLogado.toString();
        }

        User user = userRepository.getByUsername(nome);
        if(user == null) {
            user = new User();
            user.setName(nome);

            Role role = new Role("ANONIMO");
            user.setRole(role);
        }

        return user;
    }

    public User getUser(Long id) {
        return userRepository.getById(id);
    }

    public User saveOrUpdate(User userRegister) {
        return userRepository.save(userRegister);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public List<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existUsername(String username) {
        User user = userRepository.getByUsername(username);
        return user != null;
    }

    public void deleteByid(Long id) {
        userRepository.deleteById(id);
    }

    public long countUsers() {
        return userRepository.count();
    }

    public Page<User> paginationUser(Pageable pageable){
        return userRepository.findAllUsersWhitPagination(pageable);
    }

}
