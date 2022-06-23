package project.devsbarber.model.entities;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "USUARIO", uniqueConstraints = @UniqueConstraint(columnNames = "USERNAME"))
public class User implements UserDetails {

    @Id
    @Column(name="ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="EMAIL", nullable = false)
    private String email;

    @Column(name="PASSWORD", nullable = false)
    private String password;

    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name="ENABLE", nullable = false)
    private boolean enable;

    @Column(name="USERNAME", nullable = false)
    private String username;

    @Column(name = "BIRTHDATE")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthdate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn (name = "USER_ID"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User (){
    }

    public User(String email, String password, String name, boolean enable, String username, LocalDate birthdate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.enable = enable;
        this.username = username;
        this.birthdate = birthdate;
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

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
}