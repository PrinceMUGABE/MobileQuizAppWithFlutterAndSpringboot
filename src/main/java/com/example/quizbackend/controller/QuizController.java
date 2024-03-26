package com.example.quizbackend.controller;

import com.example.quizbackend.modal.Quiz;
import com.example.quizbackend.service.QuizService;
import com.example.quizbackend.serviceImplementation.QuizServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizServiceImplementation quizService;

    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(@RequestBody Map<String, Object> quizData) {
        try {
            String name = (String) quizData.get("name");
            List<Map<String, String>> questions = (List<Map<String, String>>) quizData.get("questions");

            Quiz quiz = new Quiz(name);
            for (Map<String, String> question : questions) {
                String questionText = question.get("question");
                String answerText = question.get("answer");
                quiz.getQuestions().put(questionText, answerText);
            }

            Quiz savedQuiz = quizService.saveQuiz(quiz);
            return new ResponseEntity<>(savedQuiz, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating quiz: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public List<Quiz> getAllQuizzes() {
        return quizService.displayQuizes();
    }

    @GetMapping("/search")
    public Quiz searchQuizByName(@RequestParam String name) {
        return quizService.QuizfindQuizByName(name);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteQuiz(@PathVariable Long id)
    {
        quizService.deleteQuiz(id);
    }

    // Modify the updateQuiz method in the QuizController class
    @PutMapping("/update")
    public ResponseEntity<?> updateQuiz(@RequestBody Map<String, Object> quizData) {
        try {
            Long quizId = Long.parseLong(quizData.get("id").toString());
            String quizName = (String) quizData.get("name");
            List<Map<String, String>> questions = (List<Map<String, String>>) quizData.get("questions");

            Optional<Quiz> existingQuizOptional = quizService.findQuizById(quizId);
            if (existingQuizOptional.isPresent()) {
                Quiz existingQuiz = existingQuizOptional.get();
                existingQuiz.setName(quizName);

                Map<String, String> updatedQuestions = new HashMap<>();
                for (Map<String, String> question : questions) {
                    updatedQuestions.put(question.get("question"), question.get("answer"));
                }
                existingQuiz.setQuestions(updatedQuestions);

                Quiz updatedQuiz = quizService.saveQuiz(existingQuiz);
                return new ResponseEntity<>(updatedQuiz, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating quiz: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/questions/{id}")
    public ResponseEntity<?> getQuestionsForQuiz(@PathVariable Long id) {
        Optional<Quiz> quizOptional = quizService.findQuizById(id);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            return ResponseEntity.ok(quiz.getQuestionsList()); // Change to getQuestionsList
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}


