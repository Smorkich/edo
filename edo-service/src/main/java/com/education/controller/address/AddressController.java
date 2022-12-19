package com.education.controller.address;

import com.education.service.address.AddressService;
import io.swagger.annotations.ApiOperation;
import model.dto.AddressDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * Rest-контроллер в "edo-service", служит для отправки запросов
 * от клиента(edo-service) к другому микросервису(edo-repository) используя RestTemplate
 * и для получения данных в ответ
 */
@RestController
@RequestMapping("/api/service/address")
public class AddressController {

    /**
     * "Logger", нужен для создания логов, для удобной отладки программы
     */
    Logger logger = Logger.getLogger(AddressController.class.getName());

    /**
     * Поле "addressService" нужно для вызова Service-слоя (edo-service),
     * который нужен для связи с RestTemplate (edo-service)
     */
    AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    //GET ONE
    @ApiOperation(value = "Возвращает адрес по id", notes = "Адрес должен существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findById(@PathVariable("id") long id) {
        logger.info("Get Address from database (RestTemplate on edo-service side)");
        return new ResponseEntity<>(addressService.findById(id), HttpStatus.OK);
    }

    //GET ALL
    @ApiOperation(value = "Возвращает все адреса", notes = "Адреса должны существовать")
    @GetMapping(value = "/getAllAdresseRestTemplate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> findAll() {
        logger.info("Get All Addresse from database (RestTemplate on edo-service side)");
        return new ResponseEntity<>(addressService.findAll(), HttpStatus.OK);
    }

    //POST
    @ApiOperation(value = "Создает адрес в БД", notes = "Адрес должен существовать")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressDto> save(@RequestBody AddressDto addressDto) {
        addressService.save(addressDto);
        logger.info("Post Address to database (RestTemplate on edo-service side)");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //DELETE
    @ApiOperation(value = "Удаляет адрес из БД", notes = "Адрес должен существовать")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> delete(@PathVariable("id") long id) {
        addressService.delete(id);
        logger.info("Delete Address to database (RestTemplate on edo-service side)");
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
