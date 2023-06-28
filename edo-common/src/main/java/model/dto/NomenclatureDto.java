package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@ApiModel("Объект хранения элементов")
public class NomenclatureDto implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NomenclatureDto that = (NomenclatureDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
