import com.education.entity.Department;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
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


    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
