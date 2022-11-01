package com.service.workout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("workouts")
public class Workout {

    // @Transient
    // public static final String SEQUENCE_NAME = "workouts_sequence";

    @Id
    private String id;
    private String name;
//    private List<Excercise> excercise;
    private Date date;
}
