package com.service.workout.model;

import java.util.Date;
import java.util.List;

public record WorkoutCreationRequest(
        String name,
//        List<Excercise> excercise,
        Date date
) {
}
