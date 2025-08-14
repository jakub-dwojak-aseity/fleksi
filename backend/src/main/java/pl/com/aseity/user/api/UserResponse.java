package pl.com.aseity.user.api;

import lombok.Builder;
import lombok.Value;
import pl.com.aseity.user.domain.UserRole;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
public class UserResponse {
    private UUID id;
    private String email;
    private String phone;
    private UserRole role;
    private Boolean verified;
    private Instant createdAt;
}