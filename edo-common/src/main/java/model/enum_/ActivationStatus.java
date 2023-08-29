package model.enum_;

/**
 * Enum "ActivationStatus" - статус активации
 * <p>(Активирован, Не активирован)
 */
public enum ActivationStatus {
    ACTIVATED("Активирован"),
    NOT_ACTIVATED("Не активирован");

    /**
     * "activationStatus" - поле, созданное для того, чтобы можно было переопределить toString()
     */
    private final String activationStatus;

    /**
     * Конструктор, чтобы Enum был на русском языке
     */
    ActivationStatus(String activationStatus) { this.activationStatus = activationStatus; }
}
