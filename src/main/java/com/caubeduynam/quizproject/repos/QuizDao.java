package com.caubeduynam.quizproject.repos;

import com.caubeduynam.quizproject.entities.Question;
import com.caubeduynam.quizproject.entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizDao extends JpaRepository<Quiz, Integer> {

    List<Quiz> findByQuestionsContaining(Question questionToDelete);
}
