package com.md.scheduler.schedule;

import com.md.scheduler.users.User;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ScheduleQueryRepository extends Repository<Schedule, Long> {

    Optional<Schedule> findById(long id);

    boolean existsByNameAndOwner(String name, User owner);
    
    List<Schedule> findAll();
}
