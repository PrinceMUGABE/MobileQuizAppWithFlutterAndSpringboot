package com.example.quizbackend.service;

import com.example.quizbackend.modal.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface QuizService {
    Quiz saveQuiz(Quiz quiz);
    List<Quiz> displayQuizes();
    Optional<Quiz> findQuizById(Long id);
    Quiz QuizfindQuizByName(String quizName);
    void deleteQuiz(Long id);
    Quiz updateQuiz(Quiz quiz);


}
