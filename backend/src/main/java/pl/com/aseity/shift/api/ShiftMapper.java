package pl.com.aseity.shift.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import pl.com.aseity.shift.domain.Shift;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShiftMapper {

    ShiftResponse toResponse(Shift shift);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(source = "businessId", target = "businessId")
    Shift toEntity(ShiftCreateRequest request, UUID businessId);
}