package softuni.photostore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.photostore.model.entity.accounts.UserRole;
import softuni.photostore.model.entity.enums.UserRoleEnum;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    UserRole findByRole(UserRoleEnum user);
}
