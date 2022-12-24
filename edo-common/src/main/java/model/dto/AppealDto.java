package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Collection;

@Setter
@Getter
@ApiModel(value = "Обращение граждан")
public class AppealDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "Дата создания обращения")
    private ZonedDateTime creationDate;

    @ApiModelProperty(value = "Дата отработанного обращения")
    private ZonedDateTime archivedDate;

    @ApiModelProperty(value = "Номер обращения")
    private String number;

    @ApiModelProperty(value = "Заголовок обращения")
    private String annotation;

    @ApiModelProperty(value = "Обработчик обращения")
    private Collection<EmployeeDto> signer;

    @ApiModelProperty(value = "Создатель обращения")
    private EmployeeDto creator;

    @ApiModelProperty(value = "Адрес обращения")
    private Collection<EmployeeDto> addressee;
}
