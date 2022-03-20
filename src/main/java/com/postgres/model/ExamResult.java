package com.postgres.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamResult extends User {
    private String chinese = "";
    private String english = "";
}
