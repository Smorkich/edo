package dto;

import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel(value = "Предназначен для использования вместо FilePool")
public class FilePoolDTO {
    @ApiModelProperty(value = "Id обращения", name = "storageFileId", dataType = "Long", example = "2")
    private Long storageFileId;

    @ApiModelProperty(value = "Имя обращения", name = "name", dataType = "String", example = "Dina")
    private String name;

    @ApiModelProperty(value = "Формат файла", name = "extension", dataType = "String", example = "pdf")
    private String extension;

    @ApiModelProperty(value = "Размер обращения", name = "size", dataType = "Long", example = "34")
    private Long size;

    @ApiModelProperty(value = "Количество страниц", name = "pageCount", dataType = "Long", example = "3")
    private Long pageCount;

    @ApiModelProperty(value = "Дата создания", name = "uploadDate", dataType = "Data", example = "13.12.2022")
    private Date uploadDate;

    @ApiModelProperty(value = "Дата архивации", name = "archivedDate", dataType = "Data", example = "15.12.2022")
    private Date archivedDate;

    @ApiModelProperty(value = "user name", name = "name", dataType = "String", example = "Dina")
    private EmployeeDTO creator;
}
