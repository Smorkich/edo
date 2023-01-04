package model.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * @author Nadezhda Pupina
 */
@Getter
@Setter
@ApiModel(value = "Данные о новом пользователе")
public class ExternalEmployeeDto {

        @ApiModelProperty(value = "id")
        private Long id;
        @ApiModelProperty(value = "Имя")
        private String firstName;
        @ApiModelProperty(value = "Фамилия")
        private String lastName;
        @ApiModelProperty(value = "Отчество")
        private String middleName;
        @ApiModelProperty(value = "Адрес")
        private String address;
        @ApiModelProperty(value = "Внешний идентификатор, который будем получать из чужого хранилища")
        private Long externalId;
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
        @ApiModelProperty(value = "Статус пользователя, если удален - true")
        private boolean isDelete;
        @ApiModelProperty(value = "Адрес пользователя")
        private AddressDto AddressDto;



}
