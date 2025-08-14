package pl.com.aseity.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import pl.com.aseity.common.domain.AuditableEntity;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class User extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private Boolean verified = false;

}