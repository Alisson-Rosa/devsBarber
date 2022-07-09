package project.devsbarber.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.devsbarber.model.dto.UserRegisterDTO;
import project.devsbarber.model.entities.Role;
import project.devsbarber.model.entities.User;
import project.devsbarber.model.repository.UserRepository;

import java.util.*;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleService roleService;

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

    @Transactional(rollbackFor = Exception.class)
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

    public UserRegisterDTO getUserDTO(Long id) {
        User user = getUser(id);
        UserRegisterDTO dto = new UserRegisterDTO();

        dto.setUserId(user.getId());
        dto.setServiceTerms(user.isServiceTerms());
        dto.setBirthdate(user.getBirthdate());
        dto.setRoleId(user.getRole().getId());
        dto.setRoleName(user.getRole().getName());
        dto.setEnable(user.isEnable());
        dto.setName(user.getName());
        dto.setTelephone(user.getTelephone());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    public User saveOrUpdateUserDTO(UserRegisterDTO userDTO) {
        Long userRoleId = userDTO.getRoleId();
        Role role = roleService.get(userRoleId);

        Long userId = userDTO.getUserId();
        User user = new User();
        if(userId != null){
            user = getUser(userId);
            if(user.equals(userDTO)){
                return user;
            }
            user.setId(userId);
        }

        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setEnable(userDTO.isEnable());
        user.setTelephone(userDTO.getTelephone());
        user.setBirthdate(userDTO.getBirthdate());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(role);

        saveOrUpdate(user);
        return user;
    }
}
