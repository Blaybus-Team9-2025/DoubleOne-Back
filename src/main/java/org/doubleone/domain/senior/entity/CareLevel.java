package org.doubleone.domain.senior.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CareLevel {
    NO_GRADE("등급없음"),
    LEVEL_1("1급"),
    LEVEL_2("2급"),
    LEVEL_3("3급"),
    LEVEL_4("4급"),
    LEVEL_5("5급"),
    SUPPORT("인지지원등급");

    private final String description;

    public static CareLevel fromLevel(int level) {
        switch (level) {
            case 1: return LEVEL_1;
            case 2: return LEVEL_2;
            case 3: return LEVEL_3;
            case 4: return LEVEL_4;
            case 5: return LEVEL_5;
            default: return NO_GRADE;
        }
    }
}
