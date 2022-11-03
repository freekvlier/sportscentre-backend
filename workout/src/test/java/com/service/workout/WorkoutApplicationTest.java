package com.service.workout;

import com.service.workout.dto.ExerciseRequest;
import com.service.workout.dto.WorkoutRequest;
import com.service.workout.model.Exercise;
import com.service.workout.model.Workout;
import com.service.workout.repository.IWorkoutRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class WorkoutApplicationTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private IWorkoutRepository workoutRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
        dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateWorkout() throws Exception {
        //Arrange
        WorkoutRequest workoutRequest = getWorkoutRequest();
        String workoutRequestJson = objectMapper.writeValueAsString(workoutRequest);

        //Act
        mockMvc.perform(MockMvcRequestBuilders.post("/api/workout")
                .contentType(MediaType.APPLICATION_JSON).content(workoutRequestJson)).andExpect(status().isCreated());

        //Assert
        Assertions.assertEquals(1, workoutRepository.findAll().size());
    }

    @Test
    void getAllWorkouts() throws Exception {
        List<Workout> allWorkouts = workoutRepository.findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/workout"))
                .andExpect(status().isOk());

        //Assert
        Assertions.assertEquals(1, workoutRepository.findAll().size());
    }

    private WorkoutRequest getWorkoutRequest() {
        return WorkoutRequest.builder()
                .name(RandomStringUtils.randomAlphabetic(10))
                .exercise(getExerciseList())
                .date(new Date())
                .build();
    }

    private List<Exercise> getExerciseList() {
        List<Exercise> exerciseList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ExerciseRequest exerciseRequest = getExerciseRequest();
            Exercise exercise = Exercise.builder()
                    .exerciseType(exerciseRequest.getExerciseType())
                    .weight(exerciseRequest.getWeight())
                    .sets(exerciseRequest.getSets())
                    .reps(exerciseRequest.getReps())
                    .build();

            exerciseList.add(exercise);
        }

        return exerciseList;
    }

    private ExerciseRequest getExerciseRequest() {
        Random r = new Random();

        return ExerciseRequest.builder()
                .exerciseType(RandomStringUtils.randomAlphabetic(10))
                .weight(r.nextInt(100))
                .sets(r.nextInt(20))
                .reps(r.nextInt(10))
                .build();
    }

}