package com.service.workout.service;

import com.service.workout.dto.ExerciseRequest;
import com.service.workout.dto.WorkoutRequest;
import com.service.workout.model.Exercise;
import com.service.workout.model.Workout;
import com.service.workout.repository.IWorkoutRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WorkoutServiceTest {

    @Mock
    private IWorkoutRepository workoutRepository;

    @Captor
    private ArgumentCaptor<Workout> workoutArgumentCaptor;

    private WorkoutService workoutService;

    @BeforeEach
    public void setup(){
        workoutService = new WorkoutService(workoutRepository);
    }

    @Test
    void create() {
        //Arrange
        Date today = new Date();
        WorkoutRequest workout = WorkoutRequest.builder()
                .name("ShouldSaveWorkoutTest")
                .exercises(getRandomExercisesList())
                .date(today)
                .build();;

        //Act
        workoutService.create("1", workout);

        //Assert
        Mockito.verify(workoutRepository, Mockito.times(1)).save(workoutArgumentCaptor.capture());
        Assertions.assertThat(workoutArgumentCaptor.getValue().getName()).isEqualTo("ShouldSaveWorkoutTest");
        Assertions.assertThat(workoutArgumentCaptor.getValue().getDate()).isEqualTo(today);
    }

    private WorkoutRequest getRandomWorkoutRequest() {
        return WorkoutRequest.builder()
                .name(RandomStringUtils.randomAlphabetic(10))
                .exercises(getRandomExercisesList())
                .date(new Date())
                .build();
    }

    private List<Exercise> getRandomExercisesList() {
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