package com.example.back_end.entity.Question.ChoiceQuestion;

import com.example.back_end.entity.Question.Categories;
import lombok.Data;

@Data
public class MultipleChoiceQuestion extends ChoiceQuestion {

    public  MultipleChoiceQuestion(){
        super();
        this.setCategory(Categories.MULTIPLE_CHOICE_QUESTION);
        this.setMinSelected(1);
        this.setMaxSelected(Integer.MAX_VALUE);
    }
}
