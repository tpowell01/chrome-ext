package com.chromeext.client;

/**
 * @author Andrew Kharchenko
 */
public enum Mode {
    UNSELECTED("Unselected", "Hover cursor over on webpage"),
    PRESELECTED("Pre-selected", "Right-click to select {0}"),
    SELECTED("Selected", "Complete the form below");

    private String name;
    private String description;

    Mode(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
