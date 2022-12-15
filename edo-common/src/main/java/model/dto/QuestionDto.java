package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@ApiModel(value = "Класс-обертка для взаимодействия с иными модулями и внешними системами")
public class QuestionDto {

    @ApiModelProperty(value = "Дата создания", name = "creationDate", dataType = "ZonedDateTime", example = "13.12.2022")
    private ZonedDateTime creationDate;
    @ApiModelProperty(value = "Дата архивации", name = "archivedDate", dataType = "ZonedDateTime", example = "16.12.2022")
    private ZonedDateTime archivedDate;
    @ApiModelProperty(value = "Краткое содержание вопроса", name = "summary", dataType = "String",
            example = "Как поднять настроение программисту")
    private String summary;

}
