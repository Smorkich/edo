package com.education.controller.nomenclature;

import com.education.service.nomenclature.NomenclatureService;
import com.education.util.KeySwitcherUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.NomenclatureDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/rest/nomenclature")
public class NomenclatureController {

    private final NomenclatureService service;

    /**
     * Ищет номенклатуру по индексу /find/согл
     * @param index
     * @return
     */
    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NomenclatureDto>> findByIndex(@RequestParam("index") String index) throws MalformedURLException, URISyntaxException {
        if (index.length() < 3) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var correctIndex = KeySwitcherUtil.transliterate(index);
        log.info("Отправляем запрос {} в edo-service", correctIndex);
        var foundNomenclature = service.findByIndex(correctIndex);
        log.info("Получаем ответ из edo-service: {}", foundNomenclature);

        return new ResponseEntity<>(foundNomenclature, HttpStatus.OK);
    }
}
