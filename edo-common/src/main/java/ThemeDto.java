import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ApiModel(value = "Тема обращения")

public class ThemeDto {



    @ApiModelProperty(value = "название темы")
    private String name;

    @ApiModelProperty(value = "дата архивации")
    private Date archivedDate;

    @ApiModelProperty(value = "дата создания")
    private Date creationDate;

    @ApiModelProperty(value = "идентификатор темы")
    private int code;


}
