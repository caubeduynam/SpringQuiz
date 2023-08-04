package com.caubeduynam.quizproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizWrapper {
    
    private Integer id;
    private String quizTitle;
}
