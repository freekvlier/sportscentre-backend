package com.service.workout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/workout")
public record WorkoutController(WorkoutService workoutservice) {

    public void createWorkout(@RequestBody WorkoutCreationRequest workoutCreationRequest){
        log.info("New Workout Created {]", workoutCreationRequest);
        workoutservice.create(workoutCreationRequest);
    }
}
