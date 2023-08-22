package com.education.controller.nomenclature;


import com.education.service.nomenclature.NomenclatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.NomenclatureDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController for call rest controller from another module (edo-repository) by service.
 */
@RestController
@AllArgsConstructor
@Log4j2
@Tag(name = "Rest- контроллер для работы с номенклатурой")
@RequestMapping("/api/service/nomenclature")
public class NomenclatureController {

    private NomenclatureService service;

    /**
     * Delete entity of Nomenclature by its id
     */
    @Operation(summary = "Удаляет номенклатуру по id")
    @DeleteMapping(value = "/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        log.info("delete entity with id = {}", id);
        service.deleteById(id);
        log.info("Entity with id = {} was deleted", id);
        return new String("Nomenclature" + " №" + id + " was deleted!");
    }

    /**
     * Method searches for an entity of Nomenclature
     */
    @Operation(summary = "Ищет номенклатуру по id")
    @GetMapping(value = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NomenclatureDto> findById(@PathVariable Long id) {
        log.info("Serching entity with id = {}", id);
        NomenclatureDto nomenclatureDto = service.findById(id);
        log.info("Entity {} has been found", nomenclatureDto);
        return new ResponseEntity<>(nomenclatureDto, HttpStatus.OK);
    }

    /**
     * Ищет номенклатуру по индексу /find/согл
     *
     * @param index
     * @return
     */
    @Operation(summary = "Ищет номенклатуру по index")
    @GetMapping(value = "/index", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NomenclatureDto>> findByIndex(@RequestParam("index") String index) {
        log.info("Поиск NomenclatureDto по индексу: {}", index);
        var nomenclatureDto = service.findByIndex(index);
        log.info("Entity {} has been found", nomenclatureDto);
        return new ResponseEntity<>(nomenclatureDto, HttpStatus.OK);
    }

    /**
     * Method searches for set of entities of Nomenclature by their ids: "?id = 1,2,3,4,5,6... "
     */
    @Operation(summary = "Ищет все объекты номенклатуры по id, которые находятся в Collection<Long>")
    @GetMapping(value = "/all")
    public ResponseEntity<List<NomenclatureDto>> findAllByIdController(@RequestParam("id") String ids) {
        log.info("Searching entity with id list = {}", ids);
        List<NomenclatureDto> nomenclatureDtoList = service.findAllById(ids);
        log.info("List of entities has been identified: {}", nomenclatureDtoList);
        return new ResponseEntity<>(nomenclatureDtoList, HttpStatus.OK);
    }

    /**
     * the Method fills in the field with the value and set date
     */
    @Operation(summary = "Перемещает номенклатуру в архив(Установить дату)")
    @GetMapping("/move/{id}")
    public ResponseEntity<NomenclatureDto> move(@PathVariable Long id) {
        log.debug("Set field archived_date with actual datetime");
        service.moveToArchive(id);
        log.debug("Set field archived_date with actual datetime");
        NomenclatureDto nomenclatureDto = service.findById(id);
        return new ResponseEntity<>(nomenclatureDto, HttpStatus.OK);
    }

    /**
     * Method searches for set of entities of Nomenclature that archiveDate fields are null
     */
    @Operation(summary = "Метод ищет номенклатуру, если она не была заархивирована")
    @GetMapping("/notArch/{id}")
    public ResponseEntity<NomenclatureDto> findByIdNotArchivedController(@PathVariable Long id) {
        log.info("Searching entity with empty archived_date field");
        NomenclatureDto nomenclatureDto = service.findByIdNotArchived(id);
        log.info("Archived object has been identified: {}", nomenclatureDto);
        return new ResponseEntity<>(nomenclatureDto, HttpStatus.OK);
    }

    /**
     * Method searches for an entity of Nomenclature that archiveDate field is null
     */
    @Operation(summary = "Ищет список объектов номенклатуру, если они не были заархивированы")
    @GetMapping(value = "/notArchList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<NomenclatureDto>> findAllByIdNotArchivedController(@RequestParam("id") String ids) {
        log.info("Searching entities with empty archived_date fields");
        List<NomenclatureDto> nomenclatureDtoList = service.findAllByIdNotArchived(ids);
        log.info("Archived objects has been identified: {}", nomenclatureDtoList);
        return new ResponseEntity<>(nomenclatureDtoList, HttpStatus.OK);
    }

    /**
     * Method saves new entity in DB by accepting json-body object
     */
    @Operation(summary = "Сохраняет новый объект номенклатуры по запросу json-body")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NomenclatureDto> saveDefaultEntity(@RequestBody NomenclatureDto nomenclatureDto) {
        log.info(" DTO object of Nomenclature ({}) will be saved ", nomenclatureDto);
        service.save(nomenclatureDto);
        log.info("DTO object: {} has been saved ", nomenclatureDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
