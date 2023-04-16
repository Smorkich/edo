package com.education.controller.nomenclature;

import com.education.service.nomenclature.NomenclatureService;
import com.education.util.KeySwitcherUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.NomenclatureDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/edo/nomenclature")
public class NomenclatureController {

    private final NomenclatureService service;

    /**
     * Ищет номенклатуру по индексу /find?index=согл
     * @param index
     * @return
     */
    @GetMapping("/find/{index}")
    public ResponseEntity<NomenclatureDto> findByIndex(@PathVariable("index") String index) {
        if (index.length() < 3) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var correctIndex = KeySwitcherUtil.transliterate(index).toLowerCase();
        System.out.println(correctIndex);

        log.info("Отправляем запрос в edo-service");
        var foundNomenclature = service.findByIndex(correctIndex);
        log.info("Получаем ответ из edo-service: {}", foundNomenclature);

        return new ResponseEntity<>(foundNomenclature, HttpStatus.OK);
    }
}
