package com.codepath.simpletodo.utils;

/**
 * Created by rpraveen on 6/25/16.
 */
public enum Priority {
    LOW("LOW", 0), MEDIUM("MEDIUM", 1), HIGH("HIGH", 2);
    private String name;
    private int val;
    Priority(String name, int val) {
        this.name = name;
        this.val = val;
    }

    public static Priority getValue(String value) {
        if (value == null | value.length() == 0) return Priority.LOW;
        switch(value.toUpperCase()) {
            case "LOW":
                return Priority.LOW;
            case "MEDIUM":
                return Priority.MEDIUM;
            case "HIGH":
                return Priority.HIGH;
            default:
                return Priority.LOW;
        }

    }

    public int getVal() {
        return this.val;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return "" + val;
    }
}
