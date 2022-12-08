package com.service.workout.repository;

import com.service.workout.model.Workout;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IWorkoutRepository extends MongoRepository<Workout, String> {
}
