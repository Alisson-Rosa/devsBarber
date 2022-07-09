package project.devsbarber.model.dto;

import org.springframework.format.annotation.DateTimeFormat;
import project.devsbarber.model.entities.Role;

import java.time.LocalDate;

public class UserRegisterDTO {

    private Long userId;
    private Long roleId;
    private String roleName;
    private String email;
    private String telephone;
    private String password;
    private String name;
    private boolean enable;
    private Boolean serviceTerms;
    private Boolean changePassword;
    private String username;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthdate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean isServiceTerms() {
        return serviceTerms;
    }

    public void setServiceTerms(boolean serviceTerms) {
        this.serviceTerms = serviceTerms;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Boolean getServiceTerms() {
        return serviceTerms;
    }

    public void setServiceTerms(Boolean serviceTerms) {
        this.serviceTerms = serviceTerms;
    }

    public Boolean getChangePassword() {
        return changePassword;
    }

    public void setChangePassword(Boolean changePassword) {
        this.changePassword = changePassword;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
