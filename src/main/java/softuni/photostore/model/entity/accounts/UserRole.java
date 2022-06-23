package softuni.photostore.model.entity.accounts;

import softuni.photostore.model.entity.BaseEntity;
import softuni.photostore.model.entity.enums.UserRoleEnum;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRole extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    public UserRole() {
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public UserRole setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
