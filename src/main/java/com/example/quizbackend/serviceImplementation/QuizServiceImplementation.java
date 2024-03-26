package com.example.quizbackend.serviceImplementation;

import com.example.quizbackend.modal.Quiz;
import com.example.quizbackend.repository.QuizRepository;
import com.example.quizbackend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizServiceImplementation implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> displayQuizes() {
        return quizRepository.findAll();
    }

    @Override
    public Optional<Quiz> findQuizById(Long id) {
        return quizRepository.findById(id);
    }

    @Override
    public Quiz QuizfindQuizByName(String quizName) {
        return quizRepository.findByName(quizName);
    }

    @Override
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }
}
