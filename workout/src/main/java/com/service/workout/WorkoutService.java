package com.service.workout;

import org.springframework.stereotype.Service;

@Service
public record WorkoutService() {
    public void create(WorkoutCreationRequest request) {
        Workout workout = Workout.builder()
                .name(request.name())
//                .excercise(request.excercise())
                .date(request.date())
                .build();
        // Todo check data
    }
}
