package com.education.controller.facsimile;


import com.education.service.facsimile.FacsimileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.FacsimileDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/service/facsimile")
@AllArgsConstructor
@Tag(name = "Контроллер для работы с FacsimileDto")
public class FacsimileController  {

    private FacsimileService facsimileService;


    @Operation(summary = "Принимает facsimile, отправляет на edo-repo")
    @PostMapping(value = "/save",produces = MediaType.APPLICATION_JSON_VALUE)
    public FacsimileDto save(@RequestBody FacsimileDto facsimileDto){
        return facsimileService.save(facsimileDto);
    }
}
