package com.QuestionService.service;


import com.QuestionService.model.Question;
import com.QuestionService.model.QuestionWrapper;
import com.QuestionService.model.Response;
import com.QuestionService.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    public List<Question> getAllQuestions() {

        return questionRepo.findAll();
    }

    public List<Question> getByCategory(String category) {
        return questionRepo.findByCategory(category);
    }

    public Question addQuestion(Question question) {
        questionRepo.save(question);
        return question;
    }

    public Optional<Question> getById(int id) {
        return questionRepo.findById(id);
    }

    public void deleteQuestion(int id) {
        questionRepo.deleteById(id);
    }

    public ResponseEntity<List<Integer>> generateQuestionForQuiz(int num, String category) {
        List<Integer> questionIds = questionRepo.findRandomByCategory(category,num);
        return new  ResponseEntity<>(questionIds,HttpStatus.OK );
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(List<Integer> questionIds) {

        List<QuestionWrapper> questionWrappers = new ArrayList<>();
        for (Integer questionId : questionIds) {
            Optional<Question> question = questionRepo.findById(questionId);
            question.ifPresent(q -> {
                QuestionWrapper qw = new QuestionWrapper();
                qw.setId(q.getId());
                qw.setQuestionTitle(q.getQuestionTitle());
                qw.setOption1(q.getOption1());
                qw.setOption2(q.getOption2());
                qw.setOption3(q.getOption3());
                qw.setOption4(q.getOption4());
                questionWrappers.add(qw); // ✅ Add to list
            });
        }

        return new ResponseEntity<>(questionWrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int score = 0;
        for (Response response:responses){
            Question question = questionRepo.findById(response.getId()).get();
            if (response.getResponse().equals(question.getRightAnswer()))
                score++;
        }
        return new ResponseEntity<>(score,HttpStatus.OK);
    }
}
