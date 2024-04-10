package com.education.utils;

import java.util.ArrayList;
import java.util.List;

public class TextBuilderUtil {
    public static List<String> splitText(String text){

        List<String> parts = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentPart = new StringBuilder();

        for (String word : words) {
            if (currentPart.length() + word.length() + 1 >= 80) {
                parts.add(currentPart.toString().trim());
                currentPart = new StringBuilder();
            }
            currentPart.append(word).append(" ");
        }

        if (!currentPart.isEmpty()) {
            parts.add(currentPart.toString().trim());
        }

        return parts;
    }
}
