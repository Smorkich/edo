package com.education.controller.resolutionDeadline;

import com.education.publisher.deadlineResolution.DeadlineResolutionPublisher;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.DeadlineResolutionDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Служит для связи с сервисом ResolutionService
 */
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("api/rest/resolution/deadline")
public class DeadlineResolutionController {

    private final DeadlineResolutionPublisher deadlineResolutionPublisher;

    /**
     * Данный метод принимает и отправляет DeadlineResolutionDto в DeadlineResolutionPublisher для публикации в очереди
     */
    @ApiOperation(value = "Изменение причины переноса крайнего срока резолюции и обоснования переноса")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public void reasonForTransferDeadline(@RequestBody DeadlineResolutionDto deadlineResolutionDto) {
        log.info("Send a post-request to change the deadline resolution");
        deadlineResolutionPublisher.reasonForTransferDeadline(deadlineResolutionDto);
    }
}
