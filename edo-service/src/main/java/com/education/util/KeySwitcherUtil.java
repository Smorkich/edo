package com.education.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Утильный класс KeySwitcherUtil с методом transliterate(),
 * служит для преобразования русского текста введенного на латинской раскладке
 * в корректный вид
 *
 */
@Component
public class KeySwitcherUtil {
    private static final HashMap<Character, Character> translitMap = new HashMap<>();

    static {
        translitMap.put('q', 'й');
        translitMap.put('w', 'ц');
        translitMap.put('e', 'у');
        translitMap.put('r', 'к');
        translitMap.put('t', 'е');
        translitMap.put('y', 'н');
        translitMap.put('u', 'г');
        translitMap.put('i', 'ш');
        translitMap.put('o', 'щ');
        translitMap.put('p', 'з');
        translitMap.put('[', 'х');
        translitMap.put('{', 'Х');
        translitMap.put(']', 'ъ');
        translitMap.put('}', 'Ъ');
        translitMap.put('a', 'ф');
        translitMap.put('s', 'ы');
        translitMap.put('d', 'в');
        translitMap.put('f', 'а');
        translitMap.put('g', 'п');
        translitMap.put('h', 'р');
        translitMap.put('j', 'о');
        translitMap.put('k', 'л');
        translitMap.put('l', 'д');
        translitMap.put(';', 'ж');
        translitMap.put('\'', 'э');
        translitMap.put('z', 'я');
        translitMap.put('x', 'ч');
        translitMap.put('c', 'с');
        translitMap.put('v', 'м');
        translitMap.put('b', 'и');
        translitMap.put('n', 'т');
        translitMap.put('m', 'ь');
        translitMap.put(',', 'б');
        translitMap.put('.', 'ю');
    }

    /**
     * Транслитератор
     * @param input
     * @return String
     */
    public static String transliterate(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (translitMap.containsKey(Character.toLowerCase(ch))) {
                char transChar = translitMap.get(Character.toLowerCase(ch));
                if (Character.isUpperCase(ch)) {
                    result.append(Character.toUpperCase(transChar));
                } else {
                    result.append(transChar);
                }
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
}



