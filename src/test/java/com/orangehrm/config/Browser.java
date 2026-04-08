package com.orangehrm.config;

import java.util.Locale;

public enum Browser {
    CHROME,
    EDGE,
    FIREFOX,
    SAFARI;

    public static Browser fromName(String name) {
        if (name == null || name.isBlank()) {
            return CHROME;
        }
        String normalized = name.trim().toUpperCase(Locale.ROOT).replace('-', '_');
        try {
            return Browser.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Navegador desconocido: '" + name + "'. Usa: chrome, edge, firefox, safari.", e);
        }
    }
}
