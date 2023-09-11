package model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import model.enum_.ActivationStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@ApiModel("настройка оповещений пользователей")
public class NotificationSettingsDto {

    @ApiModelProperty("ID настроек оповещения")
    private Long id;

    @ApiModelProperty("Статус активности оповещений")
    private ActivationStatus activationOfNotification;

}
