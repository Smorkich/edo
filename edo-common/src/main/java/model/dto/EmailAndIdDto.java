package model.dto;


import lombok.Data;

/**
 * Класс DTO для хранения данных о сотруднике, включая его электронную почту
 * и идентификатор резолюции у которой наступил deadline.
 *
 * @author Arkadiy_Gumelya
 */
@Data
public class EmailAndIdDto {

    private String email;

    private Long id;

    public EmailAndIdDto(Object[] objects) {
        this.email = (String) objects[0];
        this.id = (Long) objects[1];
    }


}
