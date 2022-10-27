package com.service.workout;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class Excercise {

    private Integer id;
    private ExcerciseType excerciseType;
    private Integer weight;
    private Integer set;
    private Integer rep;
}
