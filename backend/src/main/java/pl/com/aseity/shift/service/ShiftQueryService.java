package pl.com.aseity.shift.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.com.aseity.shift.api.ShiftSearchParams;
import pl.com.aseity.shift.domain.QShift;
import pl.com.aseity.shift.domain.Shift;
import pl.com.aseity.shift.domain.ShiftStatus;
import pl.com.aseity.shift.persistence.ShiftRepository;

@Service
@RequiredArgsConstructor
public class ShiftQueryService {

    private final ShiftRepository shiftRepository;

    public Page<Shift> searchShifts(ShiftSearchParams params) {
        Predicate predicate = buildPredicate(params);
        Pageable pageable = buildPageable(params);
        return shiftRepository.findAll(predicate, pageable);
    }

    private Predicate buildPredicate(ShiftSearchParams params) {
        QShift shift = QShift.shift;
        BooleanBuilder builder = new BooleanBuilder();

        if (params.getStatus() != null) {
            builder.and(shift.status.eq(params.getStatus()));
        } else {
            // Default to OPEN shifts for public search
            builder.and(shift.status.eq(ShiftStatus.OPEN));
        }

        if (params.getCity() != null) {
            builder.and(shift.city.equalsIgnoreCase(params.getCity()));
        }

        if (params.getFrom() != null) {
            builder.and(shift.startsAt.goe(params.getFrom()));
        }

        if (params.getTo() != null) {
            builder.and(shift.endsAt.loe(params.getTo()));
        }

        return builder;
    }

    private Pageable buildPageable(ShiftSearchParams params) {
        Sort sort = parseSort(params.getSort());
        return PageRequest.of(params.getPage(), params.getSize(), sort);
    }

    private Sort parseSort(String sortParam) {
        if (sortParam == null || sortParam.isEmpty()) {
            return Sort.by(Sort.Direction.ASC, "startsAt");
        }

        String[] parts = sortParam.split(",");
        String property = parts[0];
        Sort.Direction direction = parts.length > 1 && "desc".equalsIgnoreCase(parts[1]) 
            ? Sort.Direction.DESC 
            : Sort.Direction.ASC;

        return Sort.by(direction, property);
    }
}