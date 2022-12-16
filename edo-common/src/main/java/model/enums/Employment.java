package model.enums;

/**
 * Enum "model.enums.Employment" - трудоустройство автора обращения (Безработный, Работник, Учащийся)
 */
public enum Employment {
    UNEMPLOYED("Безработный"),
    EMPLOYEE("Работник"),
    STUDENT("Учащийся");

    /**
     * Статус автора - поле, созданное для того, чтобы можно было перопределить toString()
     */
    private final String status;

    /**
     * Конструктор, чтобы Enum был на русском языке
     */
    Employment(String status) {
        this.status = status;
    }

    /**
     * Переопределенный toString() для того, чтобы Enum записывался в БД на русском языке
     */
    @Override
    public String toString() {
        return status;
    }
}
