package com.example.back_end.service;

import com.example.back_end.entity.Question.BlankQuestion.Blank;
import com.example.back_end.entity.Question.Question;
import com.example.back_end.entity.auth.Account;

public interface BlankService {
    // 得到创建blank的 userId
    Integer getUserIdOfBlank(int blankId);
    // 检查是否属于 account
    Boolean belongsToAccount(Account account, int blankId);
    // 增加blank
    String createBlank(Question question, Blank blank);
    // 删除blank
    String deleteBlank(Account account, int blankId);
    // 修改blank
    String updateBlank(Account account, Blank blank);
    // 获取blank
     Blank getBlank(Account account, int questionId);
}
