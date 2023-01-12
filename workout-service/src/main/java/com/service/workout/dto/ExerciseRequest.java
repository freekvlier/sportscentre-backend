package com.service.workout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseRequest {
    private String name;
    private Integer weight;
    private Integer sets;
    private Integer reps;
}
