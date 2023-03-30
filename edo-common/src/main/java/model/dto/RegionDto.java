package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.*;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(value = "Класс-обертка для взаимодействия с иными модулями и внешними системами")
@Builder
public class RegionDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "Идентификатор региона из внешних систем")
    private String externalId;

    @ApiModelProperty(value = "Название субъекта РФ")
    private String nameSubjectRussianFederation;

    @ApiModelProperty(value = "Дата архивации")
    private ZonedDateTime archivingDate;

    @ApiModelProperty(value = "Количество сотрудников")
    private Integer quantity;

    @ApiModelProperty(value = "Федеральный округ")
    private FederalDistrictDto federalDistrict;

    @ApiModelProperty(value = "Количество первичных отделений в регионе")
    private Integer primaryBranches;

    @ApiModelProperty(value = "Количество местных отделений в регионе")
    private Integer localBranches;
}
