package com.service.workout.controller;

import com.service.workout.model.WorkoutCreationRequest;
import com.service.workout.service.WorkoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("workout")
public record WorkoutController(WorkoutService workoutservice) {

    @PostMapping
    public void createWorkout(@RequestBody WorkoutCreationRequest workoutCreationRequest){
        log.info("New Workout Created {]", workoutCreationRequest);
        workoutservice.create(workoutCreationRequest);
    }

    @GetMapping
    public void GetAllWorkouts(){
        log.info("Get All Workouts");
        workoutservice.getAll();
    }
}
