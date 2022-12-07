package com.service.workout.controller;

import com.service.workout.dto.WorkoutRequest;
import com.service.workout.dto.WorkoutResponse;
import com.service.workout.service.BearerService;
import com.service.workout.service.WorkoutService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("workout")
public class WorkoutController {

    private final WorkoutService workoutService;
    private final BearerService bearerService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createWorkout(@RequestHeader (name="Authorization") String bearer, @RequestBody WorkoutRequest workout){
        log.info("Workoutcontroller: New Workout Create request {]", workout);
        workoutService.create(bearerService.getUserId(bearer), workout);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WorkoutResponse> GetAllWorkouts(){
        log.info("Get All Workouts");
        return workoutService.getAll();
    }

    @RequestMapping("/test")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String test(){
        return "test";
    }

    @KafkaListener(topics = "user-deletion")
    public void listener(@Payload String userId) {
        System.out.println("Deleted user: " + userId);
        //Remove all user related workouts
    }
}
