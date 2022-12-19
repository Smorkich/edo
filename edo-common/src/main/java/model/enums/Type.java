package model.enums;

/**
 * Представляет собой тип обращения
 */

public enum Type {
    RESOLUTION("Резолюция"),
    REFERRAL("Направление"),
    REQUEST("Запрос");

    /**
     * Поле для хранения значения константы
     */
    private final String category;


    /**
     * Конструктор для передачи значения константы в поле для хранения
     */
    Type(String category) {
        this.category = category;
    }

    /**
     * Возвращает заданное имя категории для константы
     */
    @Override
    public String toString() {
        return category;
    }


}