package model.dto;

import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author Nadezhda Pupina
 */
@Getter
@Setter
@ApiModel(value = "Класс-обертка для взаимодействия с иными модулями и внешними системами")
public class FilePoolDto {
    @ApiModelProperty(value = "Ключ для получения файла из хранилища", name = "storageFileId", dataType = "UUID", example = "2")
    private UUID storageFileId;

    @ApiModelProperty(value = "Имя обращения", name = "name", dataType = "String", example = "Dina")
    private String name;

    @ApiModelProperty(value = "Формат файла", name = "extension", dataType = "String", example = "pdf")
    private String extension;

    @ApiModelProperty(value = "Размер обращения", name = "size", dataType = "Long", example = "34")
    private Long size;

    @ApiModelProperty(value = "Количество страниц", name = "pageCount", dataType = "Long", example = "3")
    private Long pageCount;

    @ApiModelProperty(value = "Дата создания", name = "uploadDate", dataType = "ZonedDateTime", example = "13.12.2022")
    private ZonedDateTime uploadDate;

    @ApiModelProperty(value = "Дата архивации", name = "archivedDate", dataType = "ZonedDateTime", example = "15.12.2022")
    private ZonedDateTime archivedDate;

    @ApiModelProperty(value = "id создателя файла", name = "creator", dataType = "EmployeeDTO", example = "3")
    private EmployeeDto creator;
}
