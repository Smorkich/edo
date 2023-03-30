package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@ApiModel(value = "Класс-обертка для взаимодействия с иными модулями и внешними системами")
public class FederalDistrictDto {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "Имя региона")
    private String name;

    @ApiModelProperty(value = "Сайт региона")
    private String website;
}
