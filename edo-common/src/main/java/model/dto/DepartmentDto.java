package model.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Обьект для передачи данных")
public class DepartmentDto {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "краткое имя")
    private String shortName;
    @ApiModelProperty(value = "полное имя")
    private String fullName;
    @ApiModelProperty(value = "адресс")
    private String address;
    @ApiModelProperty(value = "номер телефона")
    private String phone;
    @ApiModelProperty(value = "внешний индификатор, который будем получать из чужого хранилища")
    private Long externalId;
    @ApiModelProperty(value = "вышестоящий департамент")
    private DepartmentDto departmentDto;
}
