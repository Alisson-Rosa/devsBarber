package project.devsbarber.model.entities;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "USUARIO", uniqueConstraints = @UniqueConstraint(columnNames = "USERNAME"))
public class User implements UserDetails {

    @Id
    @Column(name="ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="EMAIL", nullable = false)
    private String email;

    @Column(name="TELEPHONE", nullable = false)
    private String telephone;

    @Column(name="PASSWORD", nullable = false)
    private String password;

    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name="ENABLE", nullable = false)
    private boolean enable;

    @Column(name="SERVICE_TERMS")
    private boolean serviceTerms;

    @Column(name="USERNAME", nullable = false)
    private String username;

    @Column(name = "BIRTHDATE", nullable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthdate;

    @ManyToOne
    @JoinTable( name = "USER_ROLE",
                joinColumns = {@JoinColumn (name = "USER_ID", nullable = false)},
                inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", nullable = false)})
    private Role role;

    public User (){
    }

    public User(String email, String password, String name, boolean enable, String username, LocalDate birthdate, String telephone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.enable = enable;
        this.username = username;
        this.birthdate = birthdate;
        this.telephone = telephone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isServiceTerms() {
        return serviceTerms;
    }

    public void setServiceTerms(boolean serviceTerms) {
        this.serviceTerms = serviceTerms;
    }
}