package project.devsbarber.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="EMAIL", nullable = false)
    private String email;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="NAME")
    private String name;

    @Column(name="ENABLE")
    private boolean enable;

    @Column(name="USERNAME")
    private String username;

    @Column(name="BIRTHDATE")
    private LocalDate birthDate;

    private Date dateConverter;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn (name = "USER_ID"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User (){
    }

    public User(String email, String password, String name, boolean enable, String username,Date dateConverter) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.enable = enable;
        this.username = username;
        this.dateConverter = dateConverter;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDateConverter() {
        return dateConverter;
    }

    public void setDateConverter(Date dateConverter) {
        this.dateConverter = dateConverter;
        birthDate = dateConverter.toInstant().atZone(ZoneId.systemDefault() ).toLocalDate();
    }
}