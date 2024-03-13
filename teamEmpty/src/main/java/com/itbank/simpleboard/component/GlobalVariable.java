package com.itbank.simpleboard.component;

import org.springframework.stereotype.Component;

@Component
public class GlobalVariable {
    private String globalSememster = "2024년 1학기";

    public String getGlobalSememster() {
        return globalSememster;
    }

    public void setGlobalSememster(String globalSememster) {
        this.globalSememster = globalSememster;
    }
}

