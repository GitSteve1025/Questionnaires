package com.example.back_end.entity.Question;

// 问题类型
public enum Categories {
    SINGLE_CHOICE_QUESTION(0, "单选题"),
    MULTIPLE_CHOICE_QUESTION(1, "多选题"),
    BLANK_QUESTION(2, "填空题");

    int code;
    String description;

    Categories(int code, String description) {
        this.code = code;
        this.description = description;
    }
}

