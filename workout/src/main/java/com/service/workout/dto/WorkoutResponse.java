package com.service.workout.dto;

import com.service.workout.model.Exercise;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutResponse {
    private String id;
    private String name;
    private List<Exercise> exercise;
    private Date date;
}
