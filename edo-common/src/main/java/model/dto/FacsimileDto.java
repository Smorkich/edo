package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(value = "Электронная подпись")
@Builder
public class FacsimileDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "Признак архивности")
    private Boolean IsArchived;

    @ApiModelProperty(value = "Cвязь с пользователем")
    private EmployeeDto employee;

    @ApiModelProperty(value = "Cвязь с департаментом")
    private DepartmentDto department;

    @ApiModelProperty(value = "Cвязь с файлом")
    private FilePoolDto filePool_id;



}
