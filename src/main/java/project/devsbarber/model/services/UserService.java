package project.devsbarber.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.devsbarber.model.entities.User;
import project.devsbarber.repository.UserRepository;

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

        return userRepository.findByUsername(nome);
    }
}
