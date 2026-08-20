package com.QuizService.feign;

import com.QuizService.model.QuestionWrapper;
import com.QuizService.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("QUESTIONSERVICE")
public interface quizInterface {


    @GetMapping("questions/generate")
    public ResponseEntity<List<Integer>> generateQuestionForQuiz(@RequestParam int num, @RequestParam String category) ;

    @PostMapping("questions/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);

    @PostMapping("questions/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);

}
