package com.QuizService.model;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class QuizDto {

    String categoryName;
    String title;
    Integer noOfQuestions;

}
