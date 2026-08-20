package com.QuizService.service;


import com.QuizService.feign.quizInterface;
import com.QuizService.model.Question;
import com.QuizService.model.QuestionWrapper;
import com.QuizService.model.Quiz;
import com.QuizService.model.Response;
import com.QuizService.repo.QuizRepo;
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
    private QuizRepo quizRepo;

    @Autowired
    private quizInterface quizInterface;

//    @Autowired
//    private QuestionRepo questionRepo;

    public void createQuiz(String category, String title, int num) {

        //Call the generate URL
        List<Integer> questions = quizInterface.generateQuestionForQuiz(num,category).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizRepo.save(quiz);
        new ResponseEntity<>("Created a quiz successfully", HttpStatus.CREATED);
    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int Quizid) {
        Optional<Quiz> quiz = quizRepo.findById(Quizid);
        List<Integer> questionIds = quiz.get().getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questionForUser = quizInterface.getQuestionsFromId(questionIds);
        return  questionForUser;
    }


      public ResponseEntity<Integer> calculateResult(int id, List<
            Response> responses) {
       ResponseEntity<Integer> score = quizInterface.getScore(responses);

         return score;
    }
}
