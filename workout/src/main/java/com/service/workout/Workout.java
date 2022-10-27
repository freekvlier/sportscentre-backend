package com.service.workout;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class Workout {

    private Integer id;
    private String name;
//    private List<Excercise> excercise;
    private Date date;
}
