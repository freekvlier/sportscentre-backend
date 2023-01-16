package com.service.workout.controller;


import com.service.workout.dto.WorkoutRequest;
import com.service.workout.dto.WorkoutResponse;
import com.service.workout.service.BearerService;
import com.service.workout.service.WorkoutService;
import io.jsonwebtoken.Jwts;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = WorkoutController.class)
@ContextConfiguration(classes = WorkoutController.class)
public class WorkoutControllerTest {

    @MockBean
    private WorkoutService workoutService;

    @MockBean
    private BearerService bearerService;
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    private String generateMockBearer() {
        return "Bearer " + Jwts.builder().claim("oid", "1").claim("roles", "Admin").compact();
    }


    @Test
    void getAllWorkouts() throws Exception {
        // Arrange
        WorkoutResponse workoutRequest1 = WorkoutResponse.builder()
                .id("id1")
                .name("GetAllWorkoutsTestRequest1")
                .exercises(new ArrayList<>())
                .date(new Date())
                .build();

        WorkoutResponse workoutRequest2 = WorkoutResponse.builder()
                .id("id2")
                .name("GetAllWorkoutsTestRequest2")
                .exercises(new ArrayList<>())
                .date(new Date())
                .build();

        Mockito.when(workoutService.getAll()).thenReturn(asList(workoutRequest1, workoutRequest2));

        // Act/Assert
        mockMvc.perform(get("/all").header("Authorization", generateMockBearer()))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(workoutRequest1.getId())))
                .andExpect(jsonPath("$[0].name", Matchers.is(workoutRequest1.getName())))
                .andExpect( jsonPath("$[1].id", Matchers.is(workoutRequest2.getId())))
                .andExpect(jsonPath("$[1].name", Matchers.is(workoutRequest2.getName())));
    }

    @Test
    void getAllWorkoutsByUserId() throws Exception {
        // Arrange
        WorkoutResponse workoutRequest1 = WorkoutResponse.builder()
                .id("id1")
                .name("getAllWorkoutsByUserIdTestRequest1")
                .exercises(new ArrayList<>())
                .date(new Date())
                .build();

        WorkoutResponse workoutRequest2 = WorkoutResponse.builder()
                .id("id2")
                .name("getAllWorkoutsByUserIdRequest2")
                .exercises(new ArrayList<>())
                .date(new Date())
                .build();

        String currentUserid = "1";

        Mockito.when(workoutService.getAllByUserId(currentUserid)).thenReturn(asList(workoutRequest1, workoutRequest2));
        Mockito.when(bearerService.getUserId(generateMockBearer())).thenReturn(currentUserid);


        // Act/Assert
        mockMvc.perform(get("/").header("Authorization", generateMockBearer()))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(workoutRequest1.getId())))
                .andExpect(jsonPath("$[0].name", Matchers.is(workoutRequest1.getName())))
                .andExpect(jsonPath("$[1].id", Matchers.is(workoutRequest2.getId())))
                .andExpect(jsonPath("$[1].name", Matchers.is(workoutRequest2.getName())));

    }

    @Test
    void createWorkout() throws Exception {
        // Arrange
        WorkoutRequest workoutRequest = WorkoutRequest.builder()
                .name("testCreateWorkout1")
                .exercises(new ArrayList<>())
                .build();

        String workoutRequestJson = objectMapper.writeValueAsString(workoutRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .header("Authorization", generateMockBearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(workoutRequestJson))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteWorkout() throws Exception {
        // Arrange
        WorkoutRequest workoutRequest = WorkoutRequest.builder()
                .name("testCreateWorkout1")
                .exercises(new ArrayList<>())
                .build();

        String workoutRequestJson = objectMapper.writeValueAsString(workoutRequest);

        // Act/Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/1")
                        .header("Authorization", generateMockBearer())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(workoutRequestJson))
                .andExpect(status().isOk());
    }


}