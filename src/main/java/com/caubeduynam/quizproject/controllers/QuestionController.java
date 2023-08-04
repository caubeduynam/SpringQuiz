package com.caubeduynam.quizproject.controllers;

import com.caubeduynam.quizproject.entities.Question;
import com.caubeduynam.quizproject.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question") //Hien thi danh sach cau hoi
    public ResponseEntity<List<Question>> getQuestions() {
        return questionService.getQuestions();
    }

    @PostMapping("question/add") //Them cau hoi
    public ResponseEntity <String> createQuestion(@RequestBody Question question) {
        return questionService.createQuestion(question);
    }

    @GetMapping("/question/id/{id}") //Hien thi cau hoi theo id
    public ResponseEntity<Optional<Question>> getQuestionByID(@PathVariable Integer id) {
        return questionService.getQuestionsByID(id);
    }

    @PutMapping("/question/id/{id}")//Cap nhat cau hoi theo id
    public ResponseEntity<String> updateQuestionById(@PathVariable Integer id, @RequestBody Question question) {
        return questionService.updateQuestionById(id, question);
    }

    @DeleteMapping("/question/id/{id}")//Xoa cau hoi
    public ResponseEntity <String> deleteQuestion(@PathVariable Integer id) {
        return questionService.deleteQuestion(id);
    }
}
