package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@ApiModel(value = "Класс-обертка для взаимодействия с иными модулями и внешними системами")
public class DeadlineResolutionDto implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "срок переноса резолюции")
    private LocalDate deadline;

    @ApiModelProperty(value = "обоснование переноса резолюции")
    private String transferDeadline;

    @ApiModelProperty(value = "Резолюция")
    private ResolutionDto resolution;
}
