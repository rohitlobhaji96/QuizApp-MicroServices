package com.QuestionService.controller;


import com.QuestionService.model.Question;
import com.QuestionService.model.QuestionWrapper;
import com.QuestionService.model.Response;
import com.QuestionService.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // ✅ Get All Questions
    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            List<Question> questions = questionService.getAllQuestions();
            if (questions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Get Questions by Category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getByCategory(@PathVariable("category") String category) {
        try {
            List<Question> questions = questionService.getByCategory(category);
            if (questions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(List.of());
            }
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Add a New Question
    @PostMapping("/addQuestion")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        try {
            Question savedQuestion = questionService.addQuestion(question);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ Update Question by ID
    @PutMapping("/updateQuestion/{id}")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question, @PathVariable("id") int id) {
        try {
            Optional<Question> existingQuestion = questionService.getById(id);
            if (existingQuestion.isPresent()) {
                Question updatedQuestion = existingQuestion.get();
                updatedQuestion.setQuestionTitle(question.getQuestionTitle());
                updatedQuestion.setCategory(question.getCategory());
                updatedQuestion.setOption1(question.getOption1());
                updatedQuestion.setOption2(question.getOption2());
                updatedQuestion.setOption3(question.getOption3());
                updatedQuestion.setOption4(question.getOption4());
                updatedQuestion.setDifficultyLevel(question.getDifficultyLevel());
                updatedQuestion.setRightAnswer(question.getRightAnswer());

                questionService.addQuestion(updatedQuestion);
                return ResponseEntity.ok("Question updated successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Question with ID " + id + " not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while updating question: " + e.getMessage());
        }
    }

    // ✅ Delete Question by ID
    @DeleteMapping("/deleteQuestion/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id") int id) {
        try {
            Optional<Question> question = questionService.getById(id);
            if (question.isPresent()) {
                questionService.deleteQuestion(id);
                return ResponseEntity.ok("Question deleted successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Question with ID " + id + " not found!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while deleting question: " + e.getMessage());
        }
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> generateQuestionForQuiz(@RequestParam int num, @RequestParam String category) {
        return questionService.generateQuestionForQuiz(num,category);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        return questionService.getQuestionFromId(questionIds);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
}