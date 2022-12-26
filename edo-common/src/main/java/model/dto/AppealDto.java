package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.enums.Employment;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Обращение граждан")
public class AppealDto implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "Дата создания обращения")
    private ZonedDateTime creationDate;

    @ApiModelProperty(value = "Дата отработанного обращения")
    private ZonedDateTime archivedDate;

    @ApiModelProperty(value = "Номер обращения")
    private String number;

    @ApiModelProperty(value = "Заголовок обращения")
    private String annotation;

    @ApiModelProperty(value = "Статус обращения")
    private EmployeeDto appealsStatusDto;

    @ApiModelProperty(value = "Способ получения обращения")
    private EmployeeDto sendingMethodDto;

    @ApiModelProperty(value = "Подписант(ы) обращения")
    private Collection<EmployeeDto> signerDto;

    @ApiModelProperty(value = "Создатель обращения")
    private EmployeeDto creatorDto;

    @ApiModelProperty(value = "Отправить им обращение тоже (в копию)")
    private Collection<EmployeeDto> addresseeDto;

    @ApiModelProperty(value = "Автор, соавторы обращения")
    private Collection<AuthorDto> authorsDto = new HashSet<>();

    @ApiModelProperty(value = "Несколько вопросов")
    private Collection<QuestionDto> questionsDto = new HashSet<>();

    @ApiModelProperty(value = "Номенклатура")
    private NomenclatureDto nomenclatureDto;

    @ApiModelProperty(value = "Несколько файлов")
    private Collection<FilePoolDto> fileDto = new HashSet<>();

}
