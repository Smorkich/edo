package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@ApiModel("Объект хранения элементов")
public class NomenclatureDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -6978024518136677460L;

    @ApiModelProperty("ID записи")
    private Long id;

    @ApiModelProperty("дата создания")
    private ZonedDateTime creationDate;

    @ApiModelProperty("дата архивации")
    private ZonedDateTime archivedDate;

    @ApiModelProperty("Название шаблона для текущего элемента")
    private String template;

    @ApiModelProperty("Текущее значение элементов")
    private Long currentValue;

    @ApiModelProperty("Индекс элемента")
    private String index;

}
