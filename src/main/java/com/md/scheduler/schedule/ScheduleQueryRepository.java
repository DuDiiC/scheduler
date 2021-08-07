package com.md.scheduler.schedule;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface ScheduleQueryRepository extends Repository<Schedule, Long> {

    Optional<Schedule> findById(long id);

    List<Schedule> findAll();
}
