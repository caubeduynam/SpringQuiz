package com.caubeduynam.quizproject.services;


import com.caubeduynam.quizproject.entities.Question;
import com.caubeduynam.quizproject.entities.Quiz;
import com.caubeduynam.quizproject.repos.QuestionRepo;
import com.caubeduynam.quizproject.repos.QuizDaoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    QuizDaoRepo quizDaoRepo;
    
    public ResponseEntity<List<Question>> getQuestions() {
        try {
            List<Question> questions = questionRepo.findAll();
            if(questions.isEmpty()) {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            }
        } catch(Exception e) {
            e.getStackTrace();

        }
        return new ResponseEntity<>(questionRepo.findAll(), HttpStatus.OK);
    }

    public ResponseEntity <String> createQuestion(Question question) {
        try {
            Example<Question> questionExample = Example.of(question);
            if(questionRepo.exists(questionExample)) {
                return new ResponseEntity<>("Question exsited !!!\nAdded Failed !!!", HttpStatus.BAD_REQUEST);
            }
        } catch(Exception e) {
            e.getStackTrace();
        }
        questionRepo.save(question);
        return new ResponseEntity<>("Added Success !!!", HttpStatus.CREATED);
    }

    public ResponseEntity <Optional<Question>> getQuestionsByID(Integer id) {
        try {
            if(questionRepo.findById(id).isPresent()) {
                return new ResponseEntity<>(questionRepo.findById(id), HttpStatus.OK);
            }
        }catch(Exception e) {
            e.getStackTrace();

        }
        return new ResponseEntity<>(Optional.empty(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity <String> updateQuestionById(Integer id, Question question) {
        try {
            if(questionRepo.existsById(id)) {
                question.setId(id);
                questionRepo.save(question);
                return new ResponseEntity<>("Updated Success", HttpStatus.OK);
            }
        }catch(NumberFormatException e) {
            e.getStackTrace();
        }
        return new ResponseEntity<>("Could not find the following ID !!!\nUpdated Failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try {
            if (questionRepo.existsById(id)) {
                Question questionToDelete = questionRepo.findById(id).orElse(null);
                if (questionToDelete != null) {
                    List<Quiz> quizzesContainingQuestion = quizDaoRepo.findByQuestionsContaining(questionToDelete);
                    if (!quizzesContainingQuestion.isEmpty()) {
                        return new ResponseEntity<>("You must delete the quiz that contains this question before deleting the question.", HttpStatus.BAD_REQUEST);
                    } else {
                        questionRepo.deleteById(id);
                        return new ResponseEntity<>("Deleted Success !!!", HttpStatus.OK);
                    }
                }
            }
        }catch(Exception e) {
            e.getStackTrace();
        }
        return new ResponseEntity<>("Could not find the following ID !!!\nDeleted Failed !!!", HttpStatus.BAD_REQUEST);
    }
}
