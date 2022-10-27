package com.service.workout;

import java.util.Date;
import java.util.List;

public record WorkoutCreationRequest(
        String name,
//        List<Excercise> excercise,
        Date date
) {
}
