package com.service.workout.service;

import com.service.workout.model.Workout;
import com.service.workout.model.WorkoutCreationRequest;
import com.service.workout.repository.IWorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public record WorkoutService(IWorkoutRepository IWorkoutRepository) {
    public void create(WorkoutCreationRequest request) {
        Workout workout = Workout.builder()
                .name(request.name())
//                .excercise(request.excercise())
                .date(request.date())
                .build();

        // Todo check data

        IWorkoutRepository.save(workout);
    }

    public void getAll() {
        Workout workout = Workout.builder()
                .name("name123")
//                .excercise(request.excercise())
                .date(new Date())
                .build();

        IWorkoutRepository.save(workout);
    }
}
