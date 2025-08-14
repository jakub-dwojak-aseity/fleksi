package pl.com.aseity.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.aseity.user.domain.User;
import pl.com.aseity.user.domain.UserRole;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    
    Optional<User> findByPhone(String phone);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhone(String phone);
    
    long countByRole(UserRole role);
}