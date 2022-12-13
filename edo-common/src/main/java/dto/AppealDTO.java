package dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;

@Setter
@Getter
@ApiModel(value = "Обращение граждан")
public class AppealDTO {

    @ApiModelProperty(value = "id")
    private long id;

    @ApiModelProperty(value = "Дата создания обращения")
    private Date creationDate;

    @ApiModelProperty(value = "Дата отработанного обращения")
    private Date archivedDate;

    @ApiModelProperty(value = "Номер обращения")
    private int number;

    @ApiModelProperty(value = "Заголовок обращения")
    private String annotation;

    @ApiModelProperty(value = "Обработчик обращения")
    private Set<Employee> signer = new HashSet<>();

    @ApiModelProperty(value = "Автор обращения")
    private Employee creator;

    @ApiModelProperty(value = "Адрес обращения")
    private Set<Employee> addressee = new HashSet<>();
}
