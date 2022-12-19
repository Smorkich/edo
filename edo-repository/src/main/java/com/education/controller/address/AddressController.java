package com.education.controller.address;

import com.education.entity.Address;

import com.education.serivce.address.AddressService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import model.dto.AddressDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Rest-контроллер в "edo-repository", служит для отправки запросов
 * от клиента(которым может быть другой микросервис) к БД
 */
@RestController
@RequestMapping("/api/repository/address")
public class AddressController {

    /**
     * "Logger", нужен для создания логов, для удобной отладки программы
     */
    Logger logger = Logger.getLogger(AddressController.class.getName());

    /**
     * Поле "addressService" нужно для вызова Service-слоя (edo-repository),
     * который нужен для связи с репозиторием (edo-repository)
     */
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    //GET ONE
    @ApiOperation(value = "Возвращает адрес по id", notes = "Адрес должен существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressDto> findById(@PathVariable("id") long id) {
        logger.info("Get Address from database");
        return new ResponseEntity<>(toDTO(addressService.findById(id)), HttpStatus.OK);
    }


    //GET ALL
    @ApiOperation(value = "Возвращает все адреса", notes = "Адреса должны существовать")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressDto>> findAll() {
        logger.info("Get All Addresse from database");
        return new ResponseEntity<>(ListAddressDto(addressService.findAll()), HttpStatus.OK);
    }

    //POST
    @ApiOperation(value = "Создает адрес в БД", notes = "Адрес должен существовать")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressDto> save(@RequestBody @Valid AddressDto addressDto) {
        addressService.save(toEntity(addressDto));
        logger.info("Post Address to database");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //DELETE
    @ApiOperation(value = "Удаляет адрес из БД", notes = "Адрес должен существовать")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") long id) {
        addressService.delete(addressService.findById(id));
        logger.info("Delete Address to database");
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Маппинг сущности "Address" в DTO "AddressDto"
     */
    public AddressDto toDTO(Address address) {
        return new AddressDto(
                address.getId(),
                address.getFullAddress(),
                address.getStreet(),
                address.getHouse(),
                address.getIndex(),
                address.getHousing(),
                address.getBuilding(),
                address.getCity(),
                address.getRegion(),
                address.getCountry(),
                address.getFlat()
        );
    }

    /**
     * Маппинг листа сущностей "Address" в лист DTO "AddressDto"
     */
    public List<AddressDto> ListAddressDto(List<Address> addresses) {
        return addresses.stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Маппинг DTO "AddressDto" в сущность "Address"
     */
    public Address toEntity(AddressDto addressDto) {
        return new Address(
                addressDto.getFullAddress(),
                addressDto.getStreet(),
                addressDto.getHouse(),
                addressDto.getIndex(),
                addressDto.getHousing(),
                addressDto.getBuilding(),
                addressDto.getCity(),
                addressDto.getRegion(),
                addressDto.getCountry(),
                addressDto.getFlat()
        );
    }
}
