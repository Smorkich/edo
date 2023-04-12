package com.education.transliterator;

import com.education.util.KeySwitcherUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Тест для утильного метода TransliteratorUtil.transliterate()
 */
public class KeySwitcherUtilTest {

    private static String CYRILLIC_WORD = "печенька";
    private static String CYRILLIC_WORD_IN_LATIN = "gtxtymrf";
    private static String CYRILLIC_SENTENCE = "печенька сочно хрустит";
    private static String CYRILLIC_SENTENCE_IN_LATIN = "gtxtymrf cjxyj [hecnbn";
    private static String CYRILLIC_SENTENCE_UPPER_CASE = "Печенька Сочно Хрустит";
    private static String CYRILLIC_SENTENCE_UPPER_CASE_IN_LATIN = "Gtxtymrf Cjxyj {hecnbn";
    private static String CYRILLIC_SENTENCE_WITH_PUNCTUATION = "печенька, сочно хрустит";
    private static String CYRILLIC_SENTENCE_IN_LATIN_WITH_PUNCTUATION = "gtxtymrf, cjxyj [hecnbn";

    @Test
    @DisplayName("Транслитерация одного слова к кириллице, позитивный сценарий")
    public void latinWordToCyrillicPositiveTest(){
        Assertions.assertEquals(CYRILLIC_WORD, KeySwitcherUtil.transliterate(CYRILLIC_WORD_IN_LATIN));
    }

    @Test
    @DisplayName("Транслитерация предложения к кириллице, позитивный сценарий")
    public void latinSentenceToCyrillicPositiveTest() {
        Assertions.assertEquals(CYRILLIC_SENTENCE, KeySwitcherUtil.transliterate(CYRILLIC_SENTENCE_IN_LATIN));
    }

    @Test
    @DisplayName("Транслитерация предложения к кириллице, слова начинаются с заглавной буквы, позитивный сценарий")
    public void latinSentenceToCyrillicWithUpperCasePositiveTest() {
        Assertions.assertEquals(CYRILLIC_SENTENCE_UPPER_CASE, KeySwitcherUtil.transliterate(CYRILLIC_SENTENCE_UPPER_CASE_IN_LATIN));
    }

    @Test
    @DisplayName("Транслитерация предложения к кириллице со знаками препинания, негативный сценарий")
    public void latinSentenceToCyrillicWithPunctuationNegativeTest() {
        Assertions.assertNotEquals(CYRILLIC_SENTENCE_WITH_PUNCTUATION, KeySwitcherUtil.transliterate(CYRILLIC_SENTENCE_IN_LATIN_WITH_PUNCTUATION));
    }
}
