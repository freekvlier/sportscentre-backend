package com.service.workout.controller;


import com.service.workout.dto.WorkoutResponse;
import com.service.workout.service.WorkoutService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

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

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    void createWorkout() {
//    }

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

        // Act/AssertW
        mockMvc.perform(get("/workout"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(workoutRequest1.getId())))
                .andExpect(jsonPath("$[0].name", Matchers.is(workoutRequest1.getName())))
                .andExpect( jsonPath("$[1].id", Matchers.is(workoutRequest2.getId())))
                .andExpect(jsonPath("$[1].name", Matchers.is(workoutRequest2.getName())));
    }
}