package com.service.workout.service;

import com.service.workout.dto.ExerciseRequest;
import com.service.workout.dto.WorkoutRequest;
import com.service.workout.dto.WorkoutResponse;
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

import java.util.*;

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
    void shouldSaveWorkout() {
        //Arrange
        Date today = new Date();
        WorkoutRequest workout = WorkoutRequest.builder()
                .name("ShouldSaveWorkoutTest")
                .exercises(getRandomExercisesList())
                .build();

        //Act
        workoutService.create("1", workout);

        //Assert
        Mockito.verify(workoutRepository, Mockito.times(1)).save(workoutArgumentCaptor.capture());
        Assertions.assertThat(workoutArgumentCaptor.getValue().getName()).isEqualTo("ShouldSaveWorkoutTest");
    }

    @Test
    void shouldGetAllWorkouts() {
        //Arrange
        List<Workout> workouts = new ArrayList<>();

        Workout workout = getRandomWorkout();
        Workout workout2 = getRandomWorkout();

        workouts.add(workout);
        workouts.add(workout2);

        Mockito.when(workoutRepository.findAll()).thenReturn(workouts);

        //Act
        List<WorkoutResponse> actual = workoutService.getAll();

        //Assert
        Assertions.assertThat(actual.size()).isEqualTo(2);
        Assertions.assertThat(actual.get(0).getName()).isEqualTo(workout.getName());
        Assertions.assertThat(actual.get(1).getName()).isEqualTo(workout2.getName());
    }
    @Test
    void shouldGetAllWorkoutsByUserid() {
        //Arrange
        List<Workout> workouts = new ArrayList<>();
        Workout workout = Workout.builder()
                .id("1")
                .userId("user123")
                .name("test")
                .exercises(getRandomExercisesList())
                .date(new Date())
                .build();

        workouts.add(workout);
        Optional<List<Workout>> workouts2 = Optional.of(workouts);

        WorkoutResponse workout3 = WorkoutResponse.builder()
                .id("1")
                .name("test")
                .exercises(getRandomExercisesList())
                .date(new Date())
                .build();

        Mockito.when(workoutRepository.findAllByUserId("user123")).thenReturn(workouts2);

        //Act
        List<WorkoutResponse> actual = workoutService.getAllByUserId("user123");

        //Assert
        Assertions.assertThat(actual.size()).isEqualTo(1);
        Assertions.assertThat(workout3.getName()).isEqualTo(workout3.getName());
    }

    private WorkoutRequest getRandomWorkoutRequest() {
        return WorkoutRequest.builder()
                .name(RandomStringUtils.randomAlphabetic(10))
                .exercises(getRandomExercisesList())
                .build();
    }

    private Workout getRandomWorkout() {
        return Workout.builder().id(RandomStringUtils.randomAlphabetic(8))
                .name(RandomStringUtils.randomAlphabetic(10))
                .exercises(getRandomExercisesList())
                .date(new Date())
                .userId(RandomStringUtils.randomAlphabetic(10))
                .build();
    }

    private List<Exercise> getRandomExercisesList() {
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