package com.project.ClubMileage.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum EventAction {
    ADD("ADD"), MOD("MOD"), DELETE("DELETE");

    private final String value;

    EventAction(String value) {
        this.value = value;
    }

    @JsonCreator
    public static EventAction from(String value) {
        for (EventAction status : EventAction.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
