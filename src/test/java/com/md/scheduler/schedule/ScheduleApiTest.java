package com.md.scheduler.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.scheduler.commons.date_range.DateRange;
import com.md.scheduler.configuration.api.errors.EntityNotFoundException;
import com.md.scheduler.configuration.api.errors.ResourceAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
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
    private DateRange dateRange;

    @BeforeEach
    void setUp() {
        dateRange = new DateRange(
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

        @Nested
        @DisplayName("getAll()")
        class GetAllTest {

            @Test
            @WithMockUser(roles = "ADMIN")
            @DisplayName("Should return 200 OK status and correct empty JSON array when admin request for all schedules")
            void shouldReturnCorrectEmptyListCase_whenOwnerRequestGetAll() throws Exception {
                when(service.getAll(Mockito.any(Pageable.class)))
                        .thenReturn(Collections.emptyList());

                mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/schedules")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(0)));
            }

            @Test
            @WithMockUser(roles = "ADMIN")
            @DisplayName("Should return 200 OK status and correct JSON array when admin request for all schedules")
            void shouldReturnCorrectResultArray_whenOwnerRequestGetAll() throws Exception {
                when(service.getAll(Mockito.any(Pageable.class)))
                        .thenReturn(List.of(scheduleResponse1, scheduleResponse2));

                mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/schedules")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(2)))
                        .andExpect(jsonPath("$[0].id", is(scheduleResponse1.getId().intValue())))
                        .andExpect(jsonPath("$[1].id", is(scheduleResponse2.getId().intValue())));
            }
        }

        @Nested
        @DisplayName("getById()")
        class GetByIdTest {

            @Test
            @WithMockUser(roles = "USER", username = "user")
            @DisplayName("Should return 200 OK status and correct schedule when owner request for get by ID")
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
            @DisplayName("Should return 404 NOT_FOUND status with correct body when owner request for schedule which not exist")
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

    @Nested
    @DisplayName("Tests for commands")
    class ScheduleApiCommandsTest {

        @Nested
        @DisplayName("save()")
        class SaveTest {

            @Test
            @WithMockUser(roles = "USER", username = "user")
            @DisplayName("Should return 201 CREATED status and correct JSON after saving new schedule")
            void shouldSaveNewScheduleCorrectly() throws Exception {
                NewSchedule newSchedule = new NewSchedule(
                        "name",
                        "desc1",
                        dateRange,
                        "active"
                );
                when(service.save(Mockito.any(NewSchedule.class), anyString()))
                        .thenReturn(scheduleResponse1);

                mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newSchedule)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$").exists())
                        .andExpect(jsonPath("$.id", is(scheduleResponse1.getId().intValue())))
                        .andExpect(jsonPath("$.name", is(scheduleResponse1.getName())))
                        .andExpect(jsonPath("$.description", is(scheduleResponse1.getDescription())))
                        .andExpect(jsonPath("$.dateRange", is(not(emptyString()))))
                        .andExpect(jsonPath("$.owner.username", is("user")));
            }

            @Test
            @WithMockUser(roles = "USER", username = "user")
            @DisplayName("Should return 409 CONFLICT status when user trying create schedule which already exist")
            void shouldThrowResourceAlreadyExist_whenUserTryingCreateDuplicateSchedule() throws Exception {
                NewSchedule newSchedule = new NewSchedule(
                        "name",
                        "desc1",
                        dateRange,
                        "active"
                );
                when(service.save(Mockito.any(NewSchedule.class), anyString()))
                        .thenThrow(new ResourceAlreadyExistsException("msg"));

                mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newSchedule)))
                        .andExpect(status().isConflict())
                        .andExpect(jsonPath("$.timestamp", is(not(emptyString()))))
                        .andExpect(jsonPath("$.status", is(HttpStatus.CONFLICT.toString())))
                        .andExpect(jsonPath("$.message", is(not(emptyString()))));
            }

            @Nested
            @DisplayName("validation")
            class ValidationTest {

                @Test
                @WithMockUser(roles = "USER", username = "user")
                @DisplayName("Should return 404 BAD_REQUEST status when schedule name is null")
                void shouldThrowValidationException_whenNameIsNull() throws Exception {
                    NewSchedule newSchedule = new NewSchedule(
                            null,
                            "desc1",
                            dateRange,
                            "active"
                    );
                    when(service.save(Mockito.any(NewSchedule.class), anyString()))
                            .thenReturn(scheduleResponse1);

                    mvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/schedules")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(newSchedule)))
                            .andExpect(status().isBadRequest())
                            .andExpect(jsonPath("$").exists())
                            .andExpect(jsonPath("$.timestamp", is(not(emptyString()))))
                            .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.toString())))
                            .andExpect(jsonPath("$..field", hasItem("name")));
                }

                @Test
                @WithMockUser(roles = "USER", username = "user")
                @DisplayName("Should return 404 BAD_REQUEST status when schedule name is empty")
                void shouldThrowValidationException_whenNameIsEmpty() throws Exception {
                    NewSchedule newSchedule = new NewSchedule(
                            "",
                            "desc1",
                            dateRange,
                            "active"
                    );
                    when(service.save(Mockito.any(NewSchedule.class), anyString()))
                            .thenReturn(scheduleResponse1);

                    mvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/schedules")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(newSchedule)))
                            .andExpect(status().isBadRequest())
                            .andExpect(jsonPath("$").exists())
                            .andExpect(jsonPath("$.timestamp", is(not(emptyString()))))
                            .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.toString())))
                            .andExpect(jsonPath("$..field", hasItem("name")));
                }

                @Test
                @WithMockUser(roles = "USER", username = "user")
                @DisplayName("Should return 404 BAD_REQUEST status when schedule name is too long")
                void shouldThrowValidationException_whenNameIsTooLong() throws Exception {
                    String longName = IntStream
                            .range(1, 110)
                            .mapToObj(x -> "x")
                            .collect(Collectors.joining());
                    NewSchedule newSchedule = new NewSchedule(
                            longName,
                            "desc1",
                            dateRange,
                            "active"
                    );
                    when(service.save(Mockito.any(NewSchedule.class), anyString()))
                            .thenReturn(scheduleResponse1);

                    mvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/schedules")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(newSchedule)))
                            .andExpect(status().isBadRequest())
                            .andExpect(jsonPath("$").exists())
                            .andExpect(jsonPath("$.timestamp", is(not(emptyString()))))
                            .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.toString())))
                            .andExpect(jsonPath("$..field", hasItem("name")));
                }

                @Test
                @WithMockUser(roles = "USER", username = "user")
                @DisplayName("Should return 404 BAD_REQUEST status when schedule description is too long")
                void shouldThrowValidationException_whenDescriptionIsTooLong() throws Exception {
                    String longDescription = IntStream
                            .range(1, 5100)
                            .mapToObj(x -> "x")
                            .collect(Collectors.joining());
                    NewSchedule newSchedule = new NewSchedule(
                            "name",
                            longDescription,
                            dateRange,
                            "active"
                    );
                    when(service.save(Mockito.any(NewSchedule.class), anyString()))
                            .thenReturn(scheduleResponse1);

                    mvc.perform(MockMvcRequestBuilders
                            .post("/api/v1/schedules")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(newSchedule)))
                            .andExpect(status().isBadRequest())
                            .andExpect(jsonPath("$").exists())
                            .andExpect(jsonPath("$.timestamp", is(not(emptyString()))))
                            .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.toString())))
                            .andExpect(jsonPath("$..field", hasItem("description")));
                }
            }
        }

        @Nested
        @DisplayName("delete()")
        class DeleteTest {

            @Test
            @WithMockUser(roles = "USER", username = "user")
            @DisplayName("Should return 200 OK status after deleting schedule")
            void shouldDeleteExistingScheduleCorrectly() throws Exception {
                doNothing().when(service).delete(anyLong(), anyString());

                mvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/schedules/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$").doesNotExist());
            }

            @Test
            @WithMockUser(roles = "USER", username = "user")
            @DisplayName("Should return 404 NOT_FOUND status with correct body when owner deleting schedule which not exist")
            void shouldThrowEntityNotFound_whenUserTryingDeleteNotExistedSchedule() throws Exception {
                doThrow(new EntityNotFoundException(Schedule.class, Map.of("id", 1L)))
                        .when(service).delete(anyLong(), anyString());

                mvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/schedules/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$").exists())
                        .andExpect(jsonPath("$.timestamp", is(not(emptyString()))))
                        .andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.toString())))
                        .andExpect(jsonPath("$.message", is(not(emptyString()))))
                        .andExpect(jsonPath("$.details", is(not(emptyString()))));
            }
        }
    }
}
