package com.service.workout.repository;

import com.service.workout.model.Workout;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@DataMongoTest
public class IWorkoutRepositoryTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.2");

    @Autowired
    private IWorkoutRepository workoutRepository;

    @AfterEach
    void cleanUp() {
        this.workoutRepository.deleteAll();
    }
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldSaveWorkout(){
        //Arrange
        Workout expectedWorkout = Workout.builder()
                .id("id1")
                .name("GetAllWorkoutsTestRequest1")
                .exercises(new ArrayList<>())
                .date(new Date())
                .build();

        //Act
        Workout actualWorkout = workoutRepository.save(expectedWorkout);

        //Assert
        assertThat(actualWorkout).usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(expectedWorkout);
    }

    @Test
    void shouldDeleteAllSavedWorkoutsbyUserId(){
        //Arrange
        Workout savedWorkout1 = Workout.builder()
                .id("userid1")
                .name("GetAllWorkoutsTestRequest1")
                .exercises(new ArrayList<>())
                .date(new Date())
                .build();

        Workout savedWorkout2 = Workout.builder()
                .id("userid1")
                .name("GetAllWorkoutsTestRequest1")
                .exercises(new ArrayList<>())
                .date(new Date())
                .build();

        workoutRepository.save(savedWorkout1);
        workoutRepository.save(savedWorkout2);

        //Act
        workoutRepository.deleteAllByUserId("userid1");
        List<Workout> actualWorkouts = workoutRepository.findAllByUserId("userid1").orElse(null);

        assertThat(actualWorkouts).usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(new ArrayList<>());
    }

}