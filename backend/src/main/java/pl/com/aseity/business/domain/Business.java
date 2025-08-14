package pl.com.aseity.business.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import pl.com.aseity.common.domain.AuditableEntity;

import java.util.UUID;

@Entity
@Table(name = "businesses")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Business extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String nip;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Boolean verified = false;

}