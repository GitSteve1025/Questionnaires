package com.example.back_end.entity.Question.BlankQuestion;

// 数据类型验证 regexp
public enum Type {
    PHONE("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"),
    EMAIL("^[a-z0-9A-Z]+[-|a-z0-9A-Z._]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$");

    private String regexp;
    Type(String regexp) {
        this.regexp = regexp;
    }
}
