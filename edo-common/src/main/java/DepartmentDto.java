
import com.education.entity.Department;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@ApiModel(value = "Обьект для передачи данных")
public class DepartmentDto {
    @ApiModelProperty(value = "краткое имя")
    private String shortName;
    @ApiModelProperty(value = "полное имя")
    private String fullName;
    @ApiModelProperty(value = "адресс")
    private String address;
    @ApiModelProperty(value = "номер телефона")
    private int phone;
    @ApiModelProperty(value = "внешний индификатор, который будем получать из чужого хранилища")
    private long externalId;
    @ApiModelProperty(value = "вышестоящий департамент")
    private Department department;

}
