package com.example.online_quizz_ritzy_system.dto;

import java.util.List;

public class QuestionResponse {

    private Long id;
    private String question;
    private String subject;
    private String questionType;
    private List<String> choices;
    private List<String>correct_choice;

    public QuestionResponse() {
    }

    public QuestionResponse(String question, String subject, String questionType, List<String> choices, List<String> correct_choice) {
        this.question = question;
        this.subject = subject;
        this.questionType = questionType;
        this.choices = choices;
        this.correct_choice = correct_choice;
    }

    public QuestionResponse(Long id, String question, String questionType, String subject, List<String> choices, List<String> correct_choice) {
        this.id = id;
        this.question = question;
        this.questionType = questionType;
        this.subject = subject;
        this.choices = choices;
        this.correct_choice = correct_choice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public String getSubject() {
        return subject;
    }

    public String getQuestionType() {
        return questionType;
    }

    public List<String> getChoices() {
        return choices;
    }


    public void setQuestion(String question) {
        this.question = question;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public void setCorrectChoice(List<String> correctChoice) {

    }

    public List<String> getCorrect_choice() {
        return correct_choice;
    }

    public void setCorrect_choice(List<String> correct_choice) {
        this.correct_choice = correct_choice;
    }
}
