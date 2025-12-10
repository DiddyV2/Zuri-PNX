package com.toxic.zuri.checks;

public class CheckInfo {

    private final String name;
    private final String type;
    private final int maxVl;
    private final boolean enabled;

    public CheckInfo(String name, String type, int maxVl, boolean enabled) {
        this.name = name;
        this.type = type;
        this.maxVl = maxVl;
        this.enabled = enabled;
    }

    public String name() { return name; }

    public String type() { return type; }

    public int maxVl() { return maxVl; }

    public boolean enabled() { return enabled; }
}
