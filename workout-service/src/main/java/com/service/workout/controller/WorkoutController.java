package com.service.workout.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.service.workout.dto.WorkoutRequest;
import com.service.workout.dto.WorkoutResponse;
import com.service.workout.service.BearerService;
import com.service.workout.service.WorkoutService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;
    private final BearerService bearerService;

    @RequestMapping("/test")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String test(){
        return "workouttest";
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createWorkout(@RequestHeader (name="Authorization") String bearer, @RequestBody WorkoutRequest workout){
        log.info("Workoutcontroller: New Workout Create request {]", workout);
        workoutService.create(bearerService.getUserId(bearer), workout);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<WorkoutResponse> GetAllWorkouts(@RequestHeader (name="Authorization") String bearer) throws IllegalAccessException {
        if(isAdmin(bearer))
            return workoutService.getAll();
        else
            return new ArrayList<>();
    }


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<WorkoutResponse> GetAllWorkoutsByUserid(@RequestHeader (name="Authorization") String bearer){
        log.info("Get All Workouts By User Id");
        return workoutService.getAllByUserId(bearerService.getUserId(bearer));
    }

    @DeleteMapping("/{workoutid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteWorkout(@RequestHeader (name="Authorization") String bearer, @PathVariable String workoutid){
        workoutService.deleteWorkout(workoutid);
    }


    @KafkaListener(topics = "user-deletion")
    public void deleteUserData(@Payload String userId) {
        System.out.println("Deleted user: " + userId);
        //Remove all user related workouts
        workoutService.deleteAllUserRelatedWorkouts(userId);
    }

    private boolean isAdmin(String bearer) throws IllegalAccessException {
        DecodedJWT decodedJWT = JWT.decode(bearer.substring(7));
        String roles = decodedJWT.getClaim("roles").asString();

        if(roles.contains("Admin"))
            return true;

        return false;
    }
}
