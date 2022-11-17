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
        Workout expectedWorkout = Workout.builder()
                .id("id1")
                .name("GetAllWorkoutsTestRequest1")
                .exercises(new ArrayList<>())
                .date(new Date())
                .build();

        Workout actualWorkout = workoutRepository.save(expectedWorkout);

        assertThat(actualWorkout).usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(expectedWorkout);
    }

}