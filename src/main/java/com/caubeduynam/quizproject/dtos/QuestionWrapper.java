package com.caubeduynam.quizproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionWrapper {

    private Integer id;
    private String questionTitle;
    private String answer1;
    private String answer2;
    private String answer3;
}
