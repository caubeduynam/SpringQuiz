package com.caubeduynam.quizproject.services;

import com.caubeduynam.quizproject.dtos.QuestionWrapper;
import com.caubeduynam.quizproject.dtos.QuizWrapper;
import com.caubeduynam.quizproject.dtos.Response;
import com.caubeduynam.quizproject.entities.Question;
import com.caubeduynam.quizproject.entities.Quiz;
import com.caubeduynam.quizproject.repos.QuestionDao;
import com.caubeduynam.quizproject.repos.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(int numQ, String title) {
        try {
            if(questionDao.findAll().isEmpty() || questionDao.findAll().size() < numQ) {
                return new ResponseEntity<>("Insufficient number of questions in the system !!!\nCreated Quiz Failed !!!", HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e) {
            e.getStackTrace();
        }

        List<Question> questions = questionDao.createRandomQuestions(numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Created Quiz Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizById(Integer id) {
        try {
            if(quizDao.findById(id).isPresent()) {
                Optional<Quiz> quiz = quizDao.findById(id);
                List<Question> questionFromDB = quiz.get().getQuestions();
                List<QuestionWrapper> questionForUser = new ArrayList<>();

                for(Question q : questionFromDB) {
                    QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getAnswer1(), q.getAnswer2(), q.getAnswer3());
                    questionForUser.add(qw);
                }

                return new ResponseEntity<>(questionForUser, HttpStatus.OK);
            }
        }catch(Exception e) {
            e.getStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int j = responses.size();
        String right = "";
        String total = " /" + j;
        int k = 0;
        String notRight = "Could not calculate !!!";

        try {
            for(int i = 0; i < responses.size(); i++) {
                if(responses.get(i).getResponse().equals(questions.get(i).getRightAnswer())) {
                    k++;
                }
                right = k + total;
            }

            return new ResponseEntity<>(right, HttpStatus.OK);
        }catch(Exception e) {
            e.getStackTrace();
        }
        return new ResponseEntity<>(notRight, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuizById(Integer id) {
        try {
            if(quizDao.existsById(id)) {
                quizDao.deleteById(id);
                return new ResponseEntity<>("Deleted Success", HttpStatus.OK);
            }
        }catch(Exception e) {
            e.getStackTrace();
        }
        return new ResponseEntity<>("Could not find the following ID !!!\nDeleted Failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuizWrapper>> getQuizList() {
        List<Quiz> quizFromDB = quizDao.findAll();
        try { 
            if(quizFromDB.isEmpty())
            {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_GATEWAY);
            }
        }catch(Exception e) {
            e.getStackTrace();
        }
        List<QuizWrapper> quizList = new ArrayList<>();

        for(Quiz q : quizFromDB) {
            QuizWrapper qw = new QuizWrapper(q.getId(), q.getTitle());
            quizList.add(qw);
        }
        
        return new ResponseEntity<>(quizList, HttpStatus.OK);
    }
}
