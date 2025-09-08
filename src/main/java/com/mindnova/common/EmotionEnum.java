package com.mindnova.common;

public enum EmotionEnum {
    HAPPY("Happy"),
    EXCITED("Excited"),
    GRATEFUL("Grateful"),
    RELAX("Relax"),
    TIRED("Tired"),
    BORED("Bored"),
    DEPRESSED("Depressed"),
    ANXIOUS("Anxious"),
    HATEFUL("Hateful"),
    ANGRY("Angry"),
    STRESSED("Stressed"),
    SAD("Sad");

    private final String label;

    EmotionEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}