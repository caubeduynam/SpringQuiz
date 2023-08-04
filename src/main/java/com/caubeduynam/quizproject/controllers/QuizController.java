package com.caubeduynam.quizproject.controllers;

import com.caubeduynam.quizproject.dtos.QuestionWrapper;
import com.caubeduynam.quizproject.dtos.QuizWrapper;
import com.caubeduynam.quizproject.dtos.Response;
import com.caubeduynam.quizproject.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")//Tao quiz
    public ResponseEntity<String> createQuiz(@RequestParam int numQ, @RequestParam String title) {
        return quizService.createQuiz(numQ, title);
    }

    @GetMapping()//Hien thi danh sach cac quiz 
    public ResponseEntity<List<QuizWrapper>> getQuizList() {
        return quizService.getQuizList();
    }

    @GetMapping("/id/{id}")//Hien thi quiz theo id
    public ResponseEntity <List<QuestionWrapper>> getQuizById(@PathVariable Integer id) {
        return quizService.getQuizById(id);
    }

    @PostMapping("/submit/id/{id}")//Kiem tra ket qua
    public ResponseEntity <String> calculateResult(@PathVariable Integer id, @RequestBody List<Response> responses) {
        return quizService.calculateResult(id, responses);
    }

    @DeleteMapping("/id/{id}")//Xoa quiz theo id
    public ResponseEntity <String> deleteQuizById(@PathVariable Integer id) {
        return quizService.deleteQuizById(id);
    }
}
