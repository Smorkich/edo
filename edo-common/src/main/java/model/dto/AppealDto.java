package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import model.enums.ReceiptMethod;
import model.enums.Status;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Status appealsStatusDto;

    @ApiModelProperty(value = "Способ получения обращения")
    private ReceiptMethod sendingMethodDto;

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
