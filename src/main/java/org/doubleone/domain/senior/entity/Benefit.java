package org.doubleone.domain.senior.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

public enum Benefit {
SADEBOHOEM("4대보험"),
    TRANSPORT_SUPPORT("교통비 지원"),
    RETIREMENT_BENEFIT("퇴직급여"),
    CONDOLENCE_SUPPORT("경조사비"),
    HOLIDAY_GIFT("명절선물"),
    MEAL_SUPPORT("식사(비)지원"),
    LONG_TERM_REWARD("장기근속 장려금"),
    GOVERNMENT_SUPPORT("정부지원금"),
    HEAVY_DISABILITY_ALLOWANCE("중증가산수당"),
    DRIVER_ALLOWANCE("운전수당");

    private final String dbValue;

    Benefit(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static Benefit fromDbValue(String dbValue) {
        return Arrays.stream(Benefit.values())
                .filter(benefit -> benefit.dbValue.equals(dbValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown benefit: " + dbValue));
    }

    @Converter(autoApply = true)
    public static class BenefitConverter implements AttributeConverter<Benefit, String> {
        @Override
        public String convertToDatabaseColumn(Benefit attribute) {
            return attribute != null ? attribute.getDbValue() : null;
        }

        @Override
        public Benefit convertToEntityAttribute(String dbData) {
            return dbData != null ? Benefit.fromDbValue(dbData) : null;
        }
    }

}
