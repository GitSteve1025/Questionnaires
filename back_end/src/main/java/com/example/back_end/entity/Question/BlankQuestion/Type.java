package com.example.back_end.entity.Question.BlankQuestion;

// 数据类型验证 regexp
public enum Type {
    NULL(0, ""),
    PHONE(1, "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"),
    EMAIL(2, "^[a-z0-9A-Z]+[-|a-z0-9A-Z._]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$");

    final int code;
    final String regexp;

    Type(int code, String regexp) {
        this.code = code;
        this.regexp = regexp;
    }

    public String toString() {
        return this.regexp;
    }
}
