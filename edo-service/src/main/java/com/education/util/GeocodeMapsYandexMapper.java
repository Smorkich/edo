package com.education.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.AddressDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Zoteev Dmitry
 * Класс для маппинга json-объекта от geocode-maps.yandex в объект AddressDto
 */

@Component
@RequiredArgsConstructor
@Log4j2
public class GeocodeMapsYandexMapper {

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    @Value("${geocodeMapsYandex.url}")
    private String GEOCODE_MAPS_YANDEX_URL;

    /**
     * Static переменные для указания значения для ключа "kind" json-объектов,
     * например, {"kind": "country", "name": "Россия"}
     */
    private static final String Country = "country";

    private static final String Region = "province";

    private static final String City = "locality";

    private static final String Street = "street";

    private static final String House = "house";

    /**
     * У json-объектов с парой "kind": "other" могут быть разные значения для ключа "name",
     * например, "name": "этаж 3" или "name": "кв. 15"
     */
    private static final String FlatOrFloor = "other";


    /**
     * Метод, который преобразует дерево json-объектов, полученных от geocode-maps.yandex,
     * в объект AddressDto
     */
    public AddressDto toAddressDto(String fullAddress) {
        var addressDto = new AddressDto();
        try {
            var geoObject = objectMapper
                    .readTree(restTemplate.getForObject(GEOCODE_MAPS_YANDEX_URL + fullAddress, String.class))
                    .path("response")
                    .path("GeoObjectCollection")
                    .path("featureMember")
                    .get(0)
                    .path("GeoObject");
            var coordinates = geoObject
                    .path("Point")
                    .path("pos").asText().split(" ");
            addressDto.setLatitude(coordinates[0]);
            addressDto.setLongitude(coordinates[1]);
            var geocoderMetaData = geoObject
                    .path("metaDataProperty")
                    .path("GeocoderMetaData");
            addressDto.setFullAddress(geocoderMetaData
                    .path("text").asText());
            var index = geocoderMetaData
                    .path("Address")
                    .path("postal_code").asInt();
            if (index != 0) {
                addressDto.setIndex(index);
            }
//      Обработка массива "Components" с json объектами, которые содержат информацию об адресе
            var addressComponents = geocoderMetaData
                    .path("Address")
                    .path("Components");
            for (JsonNode component : addressComponents) {
                var value = component.get("name").asText();
                switch (component.get("kind").asText()) {
                    case Country:
                        addressDto.setCountry(value);
                        break;
                    case Region:
                        addressDto.setRegion(value);
                        break;
                    case City:
                        addressDto.setCity(value);
                        break;
                    case Street:
                        addressDto.setStreet(value);
                        break;
                    case House:
                        setHousingAndBuilding(addressDto, value);
                        break;
                    case FlatOrFloor:
                        if (value.contains("кв.")) {
                            addressDto.setFlat(value.split("кв. ")[1]);
                        }
                        break;
                }
            }
        } catch (JsonProcessingException e) {
            log.warn("Error processing JSON data");
        }
        return addressDto;
    }

    /**
     * Вспомогательный метод для выделения номера корпуса и строения из номера дома
     * и установление этих значений в поля housing и building объекта AddressDto
     */
    public void setHousingAndBuilding(AddressDto addressDto, String value) {
        var kIndex = value.indexOf('к'); // номер индекса символа "к", означающего корпус
        var sIndex = value.indexOf('с'); // номер индекса символа "с", означающего строение
        if (kIndex == -1 && sIndex == -1) { // если указан только номер дома
            addressDto.setHouse(value);
        }
        if (kIndex > -1 && sIndex == -1) { // если указан дополнительно корпус
            addressDto.setHouse(value.substring(0, kIndex));
            addressDto.setHousing(value.substring(kIndex + 1));
        }
        if (kIndex == -1 && sIndex > -1) { // если указано дополнительно строение
            addressDto.setHouse(value.substring(0, sIndex));
            addressDto.setBuilding(value.substring(sIndex + 1));
        }
        if (kIndex > -1 && sIndex > -1) { // если указаны и корпус, и строение
            addressDto.setHouse(value.substring(0, kIndex));
            addressDto.setHousing(value.substring(kIndex + 1, sIndex));
            addressDto.setBuilding(value.substring(sIndex + 1));
        }
    }
}
