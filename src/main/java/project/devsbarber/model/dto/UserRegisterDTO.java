package project.devsbarber.model.dto;

import project.devsbarber.model.entities.Role;
import project.devsbarber.model.entities.User;

public class UserRegisterDTO {

    private User user;
    private Role role;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
