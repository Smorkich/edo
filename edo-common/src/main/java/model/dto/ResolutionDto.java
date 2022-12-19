package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import model.enums.Type;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "Класс-обертка класса Resolution для передачи данных между модулями и классами")
public class ResolutionDto ResolutionDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "Дата создания")
    private ZonedDateTime creationDate;

    @ApiModelProperty(value = "Дата архивации")
    private ZonedDateTime archivedDate;

    @ApiModelProperty(value = "Дата последнего действия")
    private ZonedDateTime lastActionDate;

    @ApiModelProperty(value = "Вид - резолюция, направление или запрос")
    private Type type;

    @ApiModelProperty(value = "Создатель")
    private EmployeeDto creatorDto;

    @ApiModelProperty(value = "Принявший резолюцию")
    private EmployeeDto signerDto;

    @ApiModelProperty(value = "Исполнители резолюции")
    private List<EmployeeDto> executorDto;

    @ApiModelProperty(value = "Контролер исполнения")
    private EmployeeDto curatorDto;

    @ApiModelProperty(value = "Серийный номер")
    private String serialNumber;


}