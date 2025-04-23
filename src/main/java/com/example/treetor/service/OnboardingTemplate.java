package com.example.treetor.service;

import java.util.HashMap;
import java.util.Map;

public class OnboardingTemplate implements BrevoTemplate {
    private final String username;
    private final String link;

    public OnboardingTemplate(String username, String link) {
        this.username = username;
        this.link = link;
    }

    @Override
    public int template() {
        return 1;
    }

    @Override
    public Map<String, Object> params() {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("link", link);
        return params;
    }
}
