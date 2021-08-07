package com.md.scheduler.schedule;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface ScheduleCommandRepository extends Repository<Schedule, Long> {

    Optional<Schedule> findById(long id);

    Schedule save(Schedule entity);

    void delete(Schedule entity);
}
