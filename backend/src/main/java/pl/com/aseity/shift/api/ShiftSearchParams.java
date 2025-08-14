package pl.com.aseity.shift.api;

import lombok.Builder;
import lombok.Value;
import pl.com.aseity.shift.domain.ShiftStatus;

import java.time.Instant;

@Value
@Builder
public class ShiftSearchParams {
    
    private String city;
    private Instant from;
    private Instant to;
    private ShiftStatus status;
    
    @Builder.Default
    private Integer page = 0;
    
    @Builder.Default  
    private Integer size = 20;
    
    @Builder.Default
    private String sort = "startsAt,asc";
}