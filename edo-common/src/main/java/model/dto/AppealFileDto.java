package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ApiModel(value = "DTO для генерации файлов обращения")
public class AppealFileDto implements Serializable {

    @EqualsAndHashCode.Include
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "Дата создания")
    private ZonedDateTime creationDate;

    @ApiModelProperty(value = "Исполнители резолюции")
    private Collection<String> executorFIOs;

    @ApiModelProperty(value = "Дедлайн резолюции")
    private LocalDate deadlineResolution;

    @ApiModelProperty(value = "Исполнена ли резолюция")
    private String resolutionStatus;
}
