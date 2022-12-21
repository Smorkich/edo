package com.education.controller.address;

import com.education.entity.Address;

import com.education.serivce.address.AddressService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AddressDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest-контроллер в "edo-repository", служит для отправки запросов
 * от клиента(которым может быть другой микросервис) к БД.
 * "@Log4j2" нужна для создания логов, для удобной отладки программы
 */
@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/api/repository/address")
public class AddressController {

    /**
     * Поле "addressService" нужно для вызова Service-слоя (edo-repository),
     * который нужен для связи с репозиторием (edo-repository)
     */
    private final AddressService addressService;

    //GET ONE /api/repository/address/{id}
    @ApiOperation(value = "Возвращает адрес по id", notes = "Адрес должен существовать")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressDto> findById(@PathVariable("id") long id) {
        log.info("Send a get-request to get Address with id = " + id + " from database");
        AddressDto addressDto = toDto(addressService.findById(id));
        log.info("Response from database: " + addressDto);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }


    //GET ALL /api/repository/address/all
    @ApiOperation(value = "Возвращает все адреса", notes = "Адреса должны существовать")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressDto>> findAll() {
        log.info("Send a get-request to get all Addresse from database");
        List<AddressDto> addressDtos = ListAddressDto((List<Address>) addressService.findAll());
        log.info("Response from database: " + addressDtos);
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    //POST /api/repository/address
    @ApiOperation(value = "Создает адрес в БД", notes = "Адрес должен существовать")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressDto> save(@RequestBody @Valid AddressDto addressDto) {
        log.info("Send a post-request to post new Address to database");
        addressService.save(toEntity(addressDto));
        log.info("Response: " + addressDto + " was added to database");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //DELETE /api/repository/address/{id}
    @ApiOperation(value = "Удаляет адрес из БД", notes = "Адрес должен существовать")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") long id) {
        log.info("Send a delete-request to delete Address with id = " + id + " from database");
        addressService.delete(addressService.findById(id));
        log.info("Response: Address with id = " + id + " was deleted from database");
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Маппинг сущности "Address" в DTO "AddressDto"
     */
    public AddressDto toDto(Address address) {
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
                .map(this::toDto)
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
