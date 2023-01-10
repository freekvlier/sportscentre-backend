package com.service.workout.repository;

import com.service.workout.model.Workout;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface IWorkoutRepository extends MongoRepository<Workout, String> {

    Optional<List<Workout>> findAllByUserId(String userId);
    void deleteAllByUserId(String userId);
}
