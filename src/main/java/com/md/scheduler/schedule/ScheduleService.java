package com.md.scheduler.schedule;

import com.md.scheduler.configuration.api.errors.EntityNotFoundException;
import com.md.scheduler.configuration.api.errors.ResourceAlreadyExistsException;
import com.md.scheduler.users.User;
import com.md.scheduler.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleQueryRepository queryRepository;
    private final ScheduleCommandRepository commandRepository;

    List<ScheduleResponse> getAll(Pageable pageable) {
        return queryRepository.findAll(pageable)
                .stream()
                .map(ScheduleResponse::new)
                .collect(Collectors.toList());
    }

    ScheduleResponse getById(Long id, String ownerName)
            throws EntityNotFoundException, UsernameNotFoundException, AccessDeniedException {
        Schedule schedule = queryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Schedule.class, Map.of("id", id)));
        User owner = userRepository.findByUsername(ownerName)
                .orElseThrow(() -> new UsernameNotFoundException(ownerName));

        if (userIsScheduleOwner(schedule, owner)) {
            return new ScheduleResponse(schedule);
        } else {
            throw new AccessDeniedException("Only owner can delete schedule");
        }
    }

    ScheduleResponse save(NewSchedule newSchedule, String ownerName)
            throws UsernameNotFoundException, ResourceAlreadyExistsException {
        User owner = userRepository.findByUsername(ownerName)
                .orElseThrow(() -> new UsernameNotFoundException(ownerName));

        if (scheduleAlreadyExists(newSchedule, owner)) {
            throw new ResourceAlreadyExistsException("User " + ownerName + " already has a schedule with this name");
        }
        return new ScheduleResponse(commandRepository.save(new Schedule(newSchedule, owner)));
    }

    private boolean scheduleAlreadyExists(NewSchedule newSchedule, User owner) {
        return queryRepository.existsByNameAndOwner(newSchedule.getName(), owner);
    }

    private boolean userIsScheduleOwner(Schedule schedule, User user) {
        return schedule.getOwner().equals(user);
    }
}
