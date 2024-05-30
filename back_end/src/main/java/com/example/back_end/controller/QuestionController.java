package com.example.back_end.controller;

import com.example.back_end.entity.Question.Categories;
import com.example.back_end.entity.Question.Question;
import lombok.Data;

import java.util.ArrayList;

@Data
public class QuestionController {
    // 所有问题
    private ArrayList<Question> questions;

    public QuestionController() {
        questions = new ArrayList<>();
    }

    // 添加问题
    void addQuestion(Categories questionType) {
        // to do
    }

    // 添加单选
    void addSingleChoiceQuestion() {
        // to do
    }

    // 添加多选
    void addMultipleChoiceQuestion() {
        // to do
    }

    // 添加填空
    void addBlankQuestion() {
        // to do
    }

    // 删除问题
    void deleteQuestion(int questionId) {
        // to do
    }

    // 修改问题
    void modifyQuestion(int questionId) {
        // to do
    }
}
