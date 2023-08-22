package model.enum_;

/**
 * Enum "Status" - статус обращения
 * <p>(Новое, Внесены изменения, Зарегистрировано, На рассмотрении, В работе, Архив, Ожидает отправки, Выполнено)
 */
public enum Status {
    NEW_STATUS("Новое"),
    REGISTERED("Зарегистрировано"),
    UPDATED("Внесены изменения"),
    UNDER_CONSIDERATION("На рассмотрении"),
    IN_WORK("В работе"),
    ARCHIVE("Архив"),
    AWAITING_SHIPMENT("Ожидает отправки"),
    PERFORMED("Выполнено");

    /**
     * "status" - поле, созданное для того, чтобы можно было переопределить toString()
     */
    private final String status;

    /**
     * Конструктор, чтобы Enum был на русском языке
     */
    Status(String status) {
        this.status = status;
    }
}
