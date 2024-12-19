package com.wired.gestao_vagas.utils;

public class StringUtils {

    public static String generateSlug(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        return input.toLowerCase()
                .replaceAll("[áàãâä]", "a")
                .replaceAll("[éèêë]", "e")
                .replaceAll("[íìîï]", "i")
                .replaceAll("[óòõôö]", "o")
                .replaceAll("[úùûü]", "u")
                .replaceAll("[ç]", "c")
                .replaceAll("[^a-z0-9]", "-")
                .replaceAll("--+", "-")
                .replaceAll("^-|-$", "");
    }
}