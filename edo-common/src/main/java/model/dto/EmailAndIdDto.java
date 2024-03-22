package model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Класс DTO для хранения данных о сотруднике, включая его электронную почту
 * и идентификатор резолюции у которой наступил deadline.
 *
 * @author Arkadiy_Gumelya
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailAndIdDto {

    private String email;

    private Long id;

}
