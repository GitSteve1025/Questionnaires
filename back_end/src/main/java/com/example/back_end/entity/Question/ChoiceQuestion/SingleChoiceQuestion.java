package com.example.back_end.entity.Question.ChoiceQuestion;

import com.example.back_end.entity.Question.Categories;
import lombok.Data;

@Data
public class SingleChoiceQuestion extends ChoiceQuestion {

    public SingleChoiceQuestion() {
        super();
        this.setCategory(Categories.SINGLE_CHOICE_QUESTION);
        this.setMinSelected(1);
        this.setMaxSelected(1);
    }
}
