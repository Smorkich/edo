package com.education.controller.nomenclature;

import com.education.entity.Nomenclature;
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
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import static com.education.mapper.NomenclatureMapper.NOMENCLATURE_MAPPER;

/**
 * RestController of edo-repository.
 */
@AllArgsConstructor
@RestController
@Log4j2
@Tag(name = "Rest- контроллер для работы с номенклатурой")
@RequestMapping("/api/repository/nomenclature")
public class NomenclatureController {
    private NomenclatureService service;

    /**
     * Delete entity of Nomenclature by its id
     */
    @Operation(summary = "Удаляет номенклатуру по id")
    @DeleteMapping(value = "/delete/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        log.info("delete entity with id = {}", id);
        service.deleteById(id);
        log.info("Entity with id = {} was deleted", id);
        return HttpStatus.ACCEPTED;
    }

    /**
     * Method searches for an entity of Nomenclature
     */
    @Operation(summary = "Ищет номенклатуру по id")
    @GetMapping(value = "/find/{id}")
    public NomenclatureDto findById(@PathVariable Long id) {
        log.info("Searching entity with id = {}", id);
        NomenclatureDto nomenclatureDto = NOMENCLATURE_MAPPER.toDto(service.findById(id));
        log.info("Entity {} has been found", nomenclatureDto);
        return nomenclatureDto;
    }

    @Operation(summary = "Ищет номенклатуру по index")
    @GetMapping(value = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<NomenclatureDto> findByIndex(@RequestParam("index") String index) throws UnsupportedEncodingException {
        log.info("Поиск NomenclatureDto по индексу: {}", index);
        var decodeParam = UriUtils.decode(index, "UTF-8");
        log.info("index after decode {}", decodeParam);
        Collection<NomenclatureDto> nomenclatureDtos = NOMENCLATURE_MAPPER.toDto(service.findByIndex(decodeParam));
        log.info("Entity {} has been found", nomenclatureDtos);
        return nomenclatureDtos;
    }

    /**
     * the Method fills in the field with the value and set date
     */
    @Operation(summary = "Перемещает номенклатуру в архив(Установить дату)")
    @GetMapping("/move/{id}")
    public NomenclatureDto move(@PathVariable Long id) {
        log.debug("Set field archived_date with actual datetime");
        service.moveToArchive(id);
        log.debug("Set field archived_date with actual datetime");
        NomenclatureDto nomenclatureDto = NOMENCLATURE_MAPPER.toDto(service.findById(id));
        return nomenclatureDto;
    }

    /**
     * Method searches for an entity of Nomenclature that archiveDate filed is null
     */
    @Operation(summary = "Метод ищет номенклатуру, если она не была заархивирована")
    @GetMapping("/notArch/{id}")
    public NomenclatureDto findByIdNotArchivedController(@PathVariable Long id) {
        log.info("Searching entity with empty archived_date field");
        NomenclatureDto nomenclatureDto = NOMENCLATURE_MAPPER.toDto(service.findByIdNotArchived(id).get());
        log.info("Archived object has been identified: {}", nomenclatureDto);
        return nomenclatureDto;
    }

    /**
     * Method searches for set of entities of Nomenclature that archiveDate fields are null
     */
    @Operation(summary = "Ищет список объектов номенклатуру, если они не были заархивированы")
    @GetMapping(value = "/notArchList", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<NomenclatureDto> findAllByIdNotArchivedController(@RequestParam("id") Collection<Long> listId) {
        log.info("Searching entities with empty archived_date fields");
        Collection<NomenclatureDto> nomenclatureDtoList = NOMENCLATURE_MAPPER.toDto(service.findAllByIdNotArchived(listId));
        log.info("Archived objects has been identified: {}", nomenclatureDtoList);
        return nomenclatureDtoList;
    }

    /**
     * Method searches for set of entities of Nomenclature by their ids in Collection
     */
    @Operation(summary = "Ищет все объекты номенклатуры по id, которые находятся в Collection<Long>")
    @GetMapping(value = "/all")
    public Collection<NomenclatureDto> findAllByIdController(@RequestParam("id") Collection<Long> listId) {
        log.info("Searching entity with id list = {}", listId);
        Collection<NomenclatureDto> nomenclatureDtoList = NOMENCLATURE_MAPPER.toDto(service.findAllById(listId));
        log.info("List of entities has been identified: {}", nomenclatureDtoList);
        return nomenclatureDtoList;
    }


    /**
     * Method saves new entity in DB by accepting jsom-body object
     */
    @Operation(summary = "Сохраняет новый объект номенклатуры по запросу json-body")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public NomenclatureDto saveDefaultEntity(@RequestBody NomenclatureDto nomenclatureDto) {
        log.info(" DTO object of Nomenclature ({}) will be saved ", nomenclatureDto);
        Nomenclature save = service.save(NOMENCLATURE_MAPPER.toEntity(nomenclatureDto));
        log.info("DTO object: {} has been saved ", nomenclatureDto);
        return nomenclatureDto;
    }
}