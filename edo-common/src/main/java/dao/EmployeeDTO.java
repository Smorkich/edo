package dao;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@ApiModel(value = "объект для передачи данных")
public class EmployeeDTO {

    @ApiModelProperty(value = "id")
    private long id;

    @ApiModelProperty(value = "Имя")
    private String firstName;

    @ApiModelProperty(value = "Фамилия")
    private String lastName;

    @ApiModelProperty(value = "Отчество")
    private String midlName;

    @ApiModelProperty(value = "Адрес")
    private String address;

    @ApiModelProperty(value = "ФИО в дательном падеже")
    private String fioDative;

    @ApiModelProperty(value = "ФИО в именительном падеже")
    private String fioNominative;

    @ApiModelProperty(value = "ФИО в родительном падеже")
    private String fioGenitive;

    @ApiModelProperty(value = "Внешний индификатор, который будем получать из чужого хранилища")
    private long externalId;

    @ApiModelProperty(value = "Номер телефона сотовый")
    private String phone;

    @ApiModelProperty(value = "Рабочий номер телефона")
    private String workPhone;

    @ApiModelProperty(value = "Дата рождения")
    private String birthDate;

    @ApiModelProperty(value = "Имя пользователя")
    private String username;

    @ApiModelProperty(value = "Дата создания")
    private ZonedDateTime creationDate;

    @ApiModelProperty(value = "Дата архивации")
    private ZonedDateTime archivedDate;

}
