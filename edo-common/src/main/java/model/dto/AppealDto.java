package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import model.enums.Employment;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.HashSet;

@Setter
@Getter
@ApiModel(value = "Обращение граждан")
public class AppealDto {

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

    @ApiModelProperty(value = "Подписант(ы) обращения")
    private Collection<EmployeeDto> signer;

    @ApiModelProperty(value = "Создатель обращения")
    private EmployeeDto creator;

    @ApiModelProperty(value = "Отправить им обращение тоже (в копию)")
    private Collection<EmployeeDto> addressee;

    @ApiModelProperty(value = "Статус обращения")
    private EmployeeDto appealsStatusDto;

    @ApiModelProperty(value = "Способ получения обращения")
    private EmployeeDto appealsReceiptMethodDto;

    @ApiModelProperty(value = "Автор, соавторы обращения")
    private Collection<AuthorDto> appealAuthorsDto = new HashSet<>();

    @ApiModelProperty(value = "Несколько файлов")
    private Collection<FilePoolDto> appealFilepoolDto = new HashSet<>();

    @ApiModelProperty(value = "Несколько вопросов")
    private Collection<QuestionDto> appealQuestionDto = new HashSet<>();

    @ApiModelProperty(value = "Номенклатура")
    private NomenclatureDto nomenclatureDto;

    @ApiModelProperty(value = "Разрешение")
    private ResolutionDto resolutionDto;

//    // !!!Не создана сущность потом раскомментить!!!
//    @ApiModelProperty(value = "Тема")
//    private ThemaDto themaDto;
}
