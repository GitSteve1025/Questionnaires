package com.example.back_end.entity.Questionnaire;

// 枚举类，表示问卷状态
public enum State {
    UNPUBLISHED(0, "未发布"),
    PUBLISHED(1, "发布中"),
    ENDED(2, "已截止");

    int code;
    String description;

    State(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
