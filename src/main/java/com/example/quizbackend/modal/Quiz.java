package com.example.quizbackend.modal;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    @MapKeyColumn(name = "question")
    @Column(name = "answer")
    @CollectionTable(name = "quiz_questions", joinColumns = @JoinColumn(name = "quiz_id"))
    private Map<String, String> questions = new HashMap<>();

    // Constructors, getters, and setters for Quiz

    public Quiz() {
        // Default constructor
    }

    public Quiz(String name) {
        this.name = name;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<String, String> questions) {
        this.questions = questions;
    }

    // Add method to get questions as a list of maps
    public List<Map<String, String>> getQuestionsList() {
        List<Map<String, String>> questionsList = new ArrayList<>();
        for (Map.Entry<String, String> entry : questions.entrySet()) {
            Map<String, String> question = new HashMap<>();
            question.put("question", entry.getKey());
            question.put("answer", entry.getValue());
            questionsList.add(question);
        }
        return questionsList;
    }
}
