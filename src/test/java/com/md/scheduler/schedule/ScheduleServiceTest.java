package com.md.scheduler.schedule;

import com.md.scheduler.commons.date_range.DateRange;
import com.md.scheduler.configuration.security.enums.AppUserRole;
import com.md.scheduler.users.User;
import com.md.scheduler.users.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @InjectMocks
    private ScheduleService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ScheduleQueryRepository queryRepository;

    @Mock
    private ScheduleCommandRepository commandRepository;

    private Schedule schedule1, schedule2;
    private NewSchedule newSchedule;
    private User scheduleOwner, anotherUser;

    @BeforeEach
    void setUp() {
        DateRange dateRange = new DateRange(
                LocalDate.of(2020, 10, 13),
                LocalDate.of(2021, 2, 11)
        );
        scheduleOwner = new User(
                "user,",
                "password",
                "email",
                AppUserRole.ROLE_USER,
                true
        );
        anotherUser = new User(
                "user",
                "password",
                "email",
                AppUserRole.ROLE_USER,
                true
        );
        schedule1 = new Schedule(
                new NewSchedule(
                        "schedule name 1",
                        "schedule desc 1",
                        dateRange,
                        "active"
                ),
                scheduleOwner
        );
        schedule2 = new Schedule(
                new NewSchedule(
                        "schedule name 2",
                        "schedule desc 2",
                        dateRange,
                        "active"),
                scheduleOwner
        );
        newSchedule = new NewSchedule(
                "schedule name",
                "schedule desc",
                dateRange,
                "active"
        );
    }

    @AfterEach
    void tearDown() {
        newSchedule = null;
        schedule1 = null;
        schedule2 = null;
        scheduleOwner = null;
        anotherUser = null;
    }
}
