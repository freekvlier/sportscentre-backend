package com.service.workout.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("excercises")
public class Excercise {


    private Integer id;
    private ExcerciseType excerciseType;
    private Integer weight;
    private Integer set;
    private Integer rep;
}
