package com.example.back_end.service;

import com.example.back_end.entity.Question.ChoiceQuestion.Choice;
import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.auth.Account;

import java.util.List;

public interface ChoiceService {
    // 得到创建选项的 userId
    Integer getUserIdOfChoice(int choiceId);
    // 检查是否属于 account
    Boolean belongsToAccount(Account account, int choiceId);
    // 增加选项
    String createChoice(Question question, Choice choice);
    // 删除选项
    String deleteChoice(Account account, int choiceId);
    // 修改选项
    String updateChoice(Account account, Choice choice);
    // 获取选项
    Choice getChoice(Account account, int choiceId);
    // 获取问题的所有选项
    List<Choice> getChoices(Account account, int questionId);
}
