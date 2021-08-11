package com.md.scheduler.schedule;

import com.md.scheduler.configuration.api.errors.EntityNotFoundException;
import com.md.scheduler.configuration.api.errors.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
class ScheduleController implements ScheduleApi {

    private final ScheduleService scheduleService;

    @Override
    public List<ScheduleResponse> getAll(Pageable pageable) {
        return scheduleService.getAll(pageable);
    }

    @Override
    public ScheduleResponse getById(Long id, Principal principal)
            throws EntityNotFoundException, UsernameNotFoundException, AccessDeniedException {
        return scheduleService.getById(id, principal.getName());
    }

    @Override
    public ScheduleResponse save(NewSchedule newSchedule, Principal principal)
            throws UsernameNotFoundException, ResourceAlreadyExistsException {
        return scheduleService.save(newSchedule, principal.getName());
    }

    @Override
    public void delete(Long id, Principal principal)
            throws UsernameNotFoundException, EntityNotFoundException, AccessDeniedException {
        scheduleService.delete(id, principal.getName());
    }
}
