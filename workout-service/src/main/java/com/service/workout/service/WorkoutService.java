package com.service.workout.service;

import com.service.workout.dto.WorkoutRequest;
import com.service.workout.dto.WorkoutResponse;
import com.service.workout.model.Workout;
import com.service.workout.repository.IWorkoutRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class WorkoutService {

    private final IWorkoutRepository workoutRepository;

    public void create(String userId, WorkoutRequest request) {
        Workout workout = Workout.builder()
                .name(request.getName())
                .exercises(request.getExercises())
                .date(new Date())
                .userId(userId)
                .build();

        // Todo check data
        log.info("Workoutservice: New Workout Create request {]", request);
        workoutRepository.save(workout);
    }

    public List<WorkoutResponse> getAll() {
        List<Workout> workouts = workoutRepository.findAll();

        return workouts.stream().map(this::mapToWorkoutResponse).toList();
    }

    public List<WorkoutResponse> getAllByUserId(String userId) {
        List<Workout> workouts = workoutRepository.findAllByUserId(userId).orElse(null);

        return workouts.stream().map(this::mapToWorkoutResponse).toList();
    }

    public void deleteAllUserRelatedWorkouts(String userid){
        workoutRepository.deleteAllByUserId(userid);
    }

    public void deleteWorkout(String workoutid){
        workoutRepository.deleteById(workoutid);
    }

    private WorkoutResponse mapToWorkoutResponse(Workout workout) {
        return WorkoutResponse.builder()
                .id(workout.getId())
                .name(workout.getName())
                .exercises(workout.getExercises())
                .date(workout.getDate())
                .build();
    }
}
