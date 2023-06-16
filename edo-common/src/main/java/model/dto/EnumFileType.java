package model.dto;

public enum EnumFileType {
    MAIN("Общий"),
    FACSIMILE("Факсимиле");

    /**
     * Поле для хранения значения константы
     */
    private final String type;


    /**
     * Конструктор для передачи значения константы в поле для хранения
     */
    EnumFileType(String type) {
        this.type = type;
    }

    /**
     * Возвращает заданное имя категории для константы
     */
    @Override
    public String toString() {
        return type;
    }

}
