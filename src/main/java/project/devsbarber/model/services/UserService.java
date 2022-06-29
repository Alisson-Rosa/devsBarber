package project.devsbarber.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.devsbarber.model.entities.User;
import project.devsbarber.repository.UserRepository;

import java.util.List;

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

        User user = userRepository.findByUsername(nome);
        if(user == null) {
            user = new User();
            user.setName(nome);
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
}
