package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "Тема обращения")

/**
 * @author AlexeySpiridonov
 */
public class ThemeDto {

    @ApiModelProperty(value = "номер id")
    private Long id;

    @ApiModelProperty(value = "название темы")
    private String name;

    @ApiModelProperty(value = "дата архивации")
    private ZonedDateTime archivedDate;

    @ApiModelProperty(value = "дата создания")
    private ZonedDateTime creationDate;

    @ApiModelProperty(value = "идентификатор темы")
    private String code;
}
