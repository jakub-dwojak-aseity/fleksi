package pl.com.aseity.application.api;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pl.com.aseity.application.domain.Application;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApplicationMapper {

    ApplicationResponse toResponse(Application application);
}