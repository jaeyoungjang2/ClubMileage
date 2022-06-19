package com.project.ClubMileage.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Event {
    ADD("add"), MOD("mod"), DELETE("delete");

    private final String value;

    Event(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Event from(String value) {
        for (Event status : Event.values()) {
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
