package pl.com.aseity.shift.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class ShiftCreateRequest {
    
    @NotBlank
    @Size(min = 3, max = 100)
    private String title;
    
    @Size(max = 4000)
    private String description;
    
    @NotNull
    @Positive
    private BigDecimal hourlyRate;
    
    @NotNull
    private Instant startsAt;
    
    @NotNull
    private Instant endsAt;
    
    @NotBlank
    private String city;
    
    @NotBlank
    private String address;
    
    @Positive
    private Integer requiredCount = 1;
}