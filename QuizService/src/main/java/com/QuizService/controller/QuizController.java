package com.QuizService.controller;


import com.QuizService.model.QuestionWrapper;
import com.QuizService.model.QuizDto;
import com.QuizService.model.Response;
import com.QuizService.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/createQuiz")
    public ResponseEntity<?> createQuiz(@RequestBody QuizDto quizDto) {
        quizService.createQuiz(quizDto.getCategoryName(),quizDto.getTitle(),quizDto.getNoOfQuestions());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable("id") int id) {

       return quizService.getQuizQuestions(id);
    }
    @PostMapping("/submitQuiz/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable("id") int id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id,responses);
    }
}
