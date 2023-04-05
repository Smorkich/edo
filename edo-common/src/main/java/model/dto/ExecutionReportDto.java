package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import model.enum_.Status;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(value = "Объект для передачи данных")
@Builder
public class ExecutionReportDto {

    @ApiModelProperty(value = "Дата исполнения резолюции")
    private ZonedDateTime creationDate;
    @ApiModelProperty(value = "Коментарий по исполнению резолюции")
    private String comment;
    @ApiModelProperty(value = "Статус исполнения резолюции")
    private Status status;
    @ApiModelProperty(value = "Исполнитель резолюции")
    private EmployeeDto executor;
    @ApiModelProperty(value = "Резолюция по которой подан отчет")
    private ResolutionDto resolution;

}

