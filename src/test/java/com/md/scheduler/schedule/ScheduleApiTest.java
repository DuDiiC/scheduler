package com.md.scheduler.schedule;

import com.md.scheduler.commons.date_range.DateRange;
import com.md.scheduler.configuration.api.errors.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ScheduleApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ScheduleService service;

    private ScheduleResponse scheduleResponse1, scheduleResponse2;

    @BeforeEach
    void setUp() {
        DateRange dateRange = new DateRange(
                LocalDate.of(2020, 10, 13),
                LocalDate.of(2021, 2, 11)
        );
        ScheduleOwner scheduleOwner = new ScheduleOwner(1L, "user");
        scheduleResponse1 = new ScheduleResponse(
                1L, "name", "desc1", "active", dateRange, scheduleOwner
        );
        scheduleResponse2 = new ScheduleResponse(
                2L, "name2", "desc2", "active", dateRange, scheduleOwner
        );
    }

    @Nested
    @DisplayName("Tests for queries")
    class ScheduleApiQueriesTest {

        @Test
        @WithMockUser(roles = "ADMIN")
        @DisplayName("ScheduleApi#getAll() - Should return correct empty JSON array when admin request for all schedules")
        void shouldReturnCorrectEmptyListCase_whenOwnerRequestGetAll() throws Exception {
            when(service.getAll(any(Pageable.class)))
                    .thenReturn(Collections.emptyList());

            mvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/schedules")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        @DisplayName("ScheduleApiTest#getAll() - Should return correct JSON array when admin request for all schedules")
        void shouldReturnCorrectResultArray_whenOwnerRequestGetAll() throws Exception {
            when(service.getAll(any(Pageable.class)))
                    .thenReturn(List.of(scheduleResponse1, scheduleResponse2));

            mvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/schedules")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].id", is(scheduleResponse1.getId().intValue())))
                    .andExpect(jsonPath("$[1].id", is(scheduleResponse2.getId().intValue())));
        }

        @Test
        @WithMockUser(roles = "USER", username = "user")
        @DisplayName("ScheduleApiTest#getById() - Should return correct schedule when owner request for get by ID")
        void shouldReturnScheduleCorrect_whenOwnerRequestGetById() throws Exception {
            when(service.getById(scheduleResponse1.getId(), scheduleResponse1.getOwner().getUsername()))
                    .thenReturn(scheduleResponse1);

            mvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/schedules/{id}", scheduleResponse1.getId())
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").exists())
                    .andExpect(jsonPath("$.id", is(scheduleResponse1.getId().intValue())))
                    .andExpect(jsonPath("$.name", is(scheduleResponse1.getName())))
                    .andExpect(jsonPath("$.description", is(scheduleResponse1.getDescription())))
                    .andExpect(jsonPath("$.status", is(scheduleResponse1.getStatus())))
                    .andExpect(jsonPath("$.owner").exists())
                    .andExpect(jsonPath("$.dateRange").exists());
        }

        @Test
        @WithMockUser(roles = "USER", username = "user")
        @DisplayName("ScheduleApiTest#getById() - Should throw EntityNotFoundException with correct body when owner request for schedule which not exist")
        void shouldThrowEntityNotFound_whenOwnerRequestGetByIdScheduleWhichNotExist() throws Exception {
            when(service.getById(1L, "user"))
                    .thenThrow(new EntityNotFoundException(Schedule.class, Map.of("id", 1L)));

            mvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/schedules/{id}", 1L)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$").exists())
                    .andExpect(jsonPath("$.timestamp", is(not(emptyString()))))
                    .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.toString())))
                    .andExpect(jsonPath("$.message", is(not(emptyString()))))
                    .andExpect(jsonPath("$.details", is(not(emptyString()))));
        }
    }
}
