package com.caubeduynam.quizproject.repos;

import com.caubeduynam.quizproject.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionDao extends JpaRepository<Question, Integer> {

    @Query(value = "SELECT * FROM questions ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
    List<Question> createRandomQuestions(int numQ);
}
