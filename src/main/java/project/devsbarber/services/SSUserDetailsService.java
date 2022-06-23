package project.devsbarber.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.devsbarber.model.entities.Role;
import project.devsbarber.model.entities.User;
import project.devsbarber.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class SSUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public SSUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);
            if(user==null){
                return null;
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthories(user));
        }catch (Exception e) {
            throw new UsernameNotFoundException("User not found!");
        }
    }

    private Set<GrantedAuthority> getAuthories(User user){
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role: user.getRoles()){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }
}