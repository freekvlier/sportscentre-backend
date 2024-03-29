package com.service.workout;

import com.service.workout.dto.ExerciseRequest;
import com.service.workout.dto.WorkoutRequest;
import com.service.workout.model.Exercise;
import com.service.workout.model.Workout;
import com.service.workout.repository.IWorkoutRepository;
import io.jsonwebtoken.Jwts;
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
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class WorkoutApplicationTest {
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.2");

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private IWorkoutRepository workoutRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
        dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        dymDynamicPropertyRegistry.add("spring.kafka.consumer.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    static {
        System.setProperty("spring.data.mongodb.database", "workout");
    }

    private String generateMockBearer() {
        return "Bearer " + Jwts.builder().claim("oid", "1").claim("roles", "Admin").compact();
    }

    @Test
    void shouldCreateWorkout() throws Exception {
        //Arrange
        WorkoutRequest workoutRequest = getWorkoutRequest();
        String workoutRequestJson = objectMapper.writeValueAsString(workoutRequest);

        //Act
        mockMvc.perform(MockMvcRequestBuilders.post("/")
                .header("Authorization", generateMockBearer())
                .contentType(MediaType.APPLICATION_JSON)
                .content(workoutRequestJson))
                .andExpect(status().isCreated());

        //Assert
        Assertions.assertEquals(1, workoutRepository.findAll().size());
    }

    @Test
    void getAllWorkouts() throws Exception {
        //Arrange
        workoutRepository.save(Workout.builder()
                .name("getAllWorkoutsTest")
                .exercises(getExerciseList())
                .date(new Date())
                .build());
        Integer workoutsCount = workoutRepository.findAll().size();

        //Act/Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/all").header("Authorization", generateMockBearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(workoutsCount)));
    }

    @Test
    void getAllWorkoutsByUserId() throws Exception {
        //Arrange
        workoutRepository.save(Workout.builder()
                .name("getAllWorkoutsTest")
                .userId("user123")
                .exercises(getExerciseList())
                .date(new Date())
                .build());
        workoutRepository.save(Workout.builder()
                .name("getAllWorkoutsTest")
                .userId("user123")
                .exercises(getExerciseList())
                .date(new Date())
                .build());
        workoutRepository.save(Workout.builder()
                .name("getAllWorkoutsTest")
                .userId("1")
                .exercises(getExerciseList())
                .date(new Date())
                .build());
        Integer workoutsCount = workoutRepository.findAllByUserId("1").get().size();

        //Act/Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/").header("Authorization", generateMockBearer()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(workoutsCount)));
    }

    private WorkoutRequest getWorkoutRequest() {
        return WorkoutRequest.builder()
                .name(RandomStringUtils.randomAlphabetic(10))
                .exercises(getExerciseList())
                .build();
    }

    private List<Exercise> getExerciseList() {
        List<Exercise> exerciseList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ExerciseRequest exerciseRequest = getExerciseRequest();
            Exercise exercise = Exercise.builder()
                    .name(exerciseRequest.getName())
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
                .name(RandomStringUtils.randomAlphabetic(10))
                .weight(r.nextInt(100))
                .sets(r.nextInt(20))
                .reps(r.nextInt(10))
                .build();
    }

}