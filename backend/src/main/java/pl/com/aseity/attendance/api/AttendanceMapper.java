package pl.com.aseity.attendance.api;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pl.com.aseity.attendance.domain.Attendance;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttendanceMapper {

    AttendanceResponse toResponse(Attendance attendance);
}