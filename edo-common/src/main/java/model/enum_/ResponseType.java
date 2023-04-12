package model.enum_;

/**
 * Enum "ResponseType" - представляет собой способ получения ответа автором
 */
public enum ResponseType {
    POST_OFFICE("Почта России"),
    SMS("СМС"),
    EMAIL("Электронная почта");

    /**
     * Поле для хранения значения константы
     */
    private final String responseType;

    /**
     * Конструктор для передачи значения константы в поле для хранения
     */
    ResponseType(String responseType) {
        this.responseType = responseType;
    }

    /**
     * Возвращает заданное имя типа для константы
     */
    @Override
    public String toString() {
        return responseType;
    }
}
