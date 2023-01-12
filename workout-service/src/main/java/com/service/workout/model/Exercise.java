package com.service.workout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("excercises")
public class Exercise {
    private String name;
    private Integer weight;
    private Integer sets;
    private Integer reps;
}
