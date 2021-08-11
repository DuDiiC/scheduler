package com.md.scheduler.schedule;

import com.md.scheduler.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ScheduleService {

    private final UserRepository userRepository;
    private final ScheduleQueryRepository queryRepository;
    private final ScheduleCommandRepository commandRepository;

}
