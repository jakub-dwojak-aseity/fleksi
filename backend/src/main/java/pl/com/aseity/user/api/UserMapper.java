package pl.com.aseity.user.api;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pl.com.aseity.user.domain.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponse toResponse(User user);
}