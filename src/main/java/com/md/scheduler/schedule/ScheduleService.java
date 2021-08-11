package com.md.scheduler.schedule;

import com.md.scheduler.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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

}
