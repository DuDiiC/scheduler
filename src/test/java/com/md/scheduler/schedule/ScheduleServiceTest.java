package com.md.scheduler.schedule;

import com.md.scheduler.commons.date_range.DateRange;
import com.md.scheduler.configuration.api.errors.EntityNotFoundException;
import com.md.scheduler.configuration.security.enums.AppUserRole;
import com.md.scheduler.users.User;
import com.md.scheduler.users.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

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

    @Nested
    @DisplayName("Tests for queries")
    class ScheduleServiceQueriesTest {

        @Test
        @DisplayName("ScheduleService#getAll(): Should return all schedules when getting all")
        void shouldReturnAllSchedules_whenGettingAll() {
            when(queryRepository.findAll(any(Pageable.class)))
                    .thenReturn(new PageImpl<>(List.of(schedule1, schedule2)));

            List<ScheduleResponse> response = service.getAll(Pageable.unpaged());
            assertEquals(2, response.size());
        }

        @Test
        @DisplayName("ScheduleService#getById(): Should return schedule by ID for owner")
        void shouldReturnScheduleById_whenOwnerSentRequest() {
            when(queryRepository.findById(anyLong()))
                    .thenReturn(Optional.of(schedule1));
            when(userRepository.findByUsername(anyString()))
                    .thenReturn(Optional.of(scheduleOwner));

            ScheduleResponse response = service.getById(1L, "");
            assertNotNull(response);
        }

        @Test
        @DisplayName("ScheduleService#getById(): Should throw EntityNotFoundException when schedule does not exist")
        void shouldThrowEntityNotFound_whenScheduleToGetDoesNotExist() {
            when(queryRepository.findById(anyLong()))
                    .thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> service.getById(1L, ""));
        }

        @Test
        @DisplayName("ScheduleService#getById(): Should throw UsernameNotFoundException when potential owner does not exist")
        void shouldThrowUsernameNotFound_whenPotentialOwnerOfExistingScheduleDoesNotExist() {
            when(queryRepository.findById(anyLong()))
                    .thenReturn(Optional.of(schedule1));
            when(userRepository.findByUsername(anyString()))
                    .thenReturn(Optional.empty());

            assertThrows(UsernameNotFoundException.class, () -> service.getById(1L, ""));
        }

        @Test
        @DisplayName("ScheduleService#getById(): Should throw AccessDeniedException when a user other than the owner sent the request")
        void shouldThrowAccessDenied_whenNonOwnerSentGetByIdRequest() {
            when(queryRepository.findById(anyLong()))
                    .thenReturn(Optional.of(schedule1));
            when(userRepository.findByUsername(anyString()))
                    .thenReturn(Optional.of(anotherUser));

            assertThrows(AccessDeniedException.class, () -> service.getById(1L, ""));
        }
    }

    @Nested
    @DisplayName("Tests for commands")
    class ScheduleServiceCommandsTest {

        @Test
        @DisplayName("ScheduleService#save() - Should create new schedule based on DTO and owner name")
        void shouldCreateNewSchedule_whenNewScheduleAndOwnerName() {
        }

        @Test
        @DisplayName("ScheduleService#save() - Should throw UsernameNotFoundException when potential owner does not exist")
        void shouldThrowUsernameNotFound_whenPotentialOwnerOfNewScheduleDoesNotExist() {
        }

        @Test
        @DisplayName("ScheduleService#save() - Should throw ResourceAlreadyExistException when selected user already has a schedule with selected name")
        void shouldThrowResourceAlreadyExist_whenPotentialNewScheduleAlreadyExist() {
        }

        @Test
        @DisplayName("ScheduleService#delete() - Should remove selected schedule when the owner sends the request")
        void shouldDeleteScheduleWhenOwnerSendsRequest() {
        }

        @Test
        @DisplayName("ScheduleService#delete() - Should throw UsernameNotFoundException when potential owner does not exist")
        void shouldThrowUsernameNotFound_whenPotentialOwnerOfScheduleToDeleteDoesNotExist() {
        }

        @Test
        @DisplayName("ScheduleService#delete() - Should throw EntityNotFoundException when schedule to delete does not exist")
        void shouldThrowUsernameNotFound_whenScheduleToDeleteDoesNotExist() {
        }

        @Test
        @DisplayName("ScheduleService#delete() - Should throw AccessDeniedException when a user other than the owner sent the request")
        void shouldThrowAccessDenied_whenNonOwnerSentDeleteRequest() {
        }
    }
}
