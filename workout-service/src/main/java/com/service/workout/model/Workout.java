package com.service.workout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Workout {

    @Id
    private String id;
    private String name;
    private List<Exercise> exercises;
    private Date date;
    private String userId;
}
