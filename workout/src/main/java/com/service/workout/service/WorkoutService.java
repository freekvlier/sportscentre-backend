package com.service.workout.service;

import com.service.workout.dto.WorkoutRequest;
import com.service.workout.dto.WorkoutResponse;
import com.service.workout.model.Workout;
import com.service.workout.repository.IWorkoutRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public record WorkoutService(IWorkoutRepository workoutRepository) {
    public void create(WorkoutRequest request) {
        Workout workout = Workout.builder()
                .name(request.getName())
                .exercises(request.getExercise())
                .date(request.getDate())
                .build();

        // Todo check data
        log.info("Workoutservice: New Workout Create request {]", request);
        workoutRepository.save(workout);
    }

    public void getAll() {
        List<Workout> workouts = workoutRepository.findAll();

        workouts.stream().map(this::mapToWorkoutResponse).toList();
    }

    private WorkoutResponse mapToWorkoutResponse(Workout workout) {
        return WorkoutResponse.builder()
                .id(workout.getId())
                .name(workout.getName())
                .exercise(workout.getExercises())
                .date(workout.getDate())
                .build();
    }
}
