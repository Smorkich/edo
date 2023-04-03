package com.education.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.dto.AddressDto;

/**
 * @author Zoteev Dmitry
 * Класс для маппинга json объекта от geocode-maps.yandex в объект AddressDto
 */

public class GeocodeMapsYandexToAddressDto {

    /**
     * Метод, который преобразует дерево объектов json, полученных от geocode-maps.yandex,
     * в объект AddressDto
     */
    public AddressDto toAddressDto(String geocodeAddress) throws JsonProcessingException {
        var addressDto = new AddressDto();
        var geoObject = new ObjectMapper()
                .readTree(geocodeAddress)
                .path("response")
                .path("GeoObjectCollection")
                .path("featureMember")
                .get(0)
                .path("GeoObject");
        var coordinates = geoObject
                .path("Point")
                .path("pos").asText();
        addressDto.setLatitude(coordinates.substring(0, coordinates.indexOf(' ')));
        addressDto.setLongitude(coordinates.substring(coordinates.indexOf(' ') + 1, coordinates.length() - 1));
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
                case "country":
                    addressDto.setCountry(value);
                    break;
                case "province":
                    addressDto.setRegion(value);
                    break;
                case "locality":
                    addressDto.setCity(value);
                    break;
                case "street":
                    addressDto.setStreet(value);
                    break;
                case "house":
                    setHousingAndBuilding(addressDto, value);
                    break;
                case "other":
                    if (value.contains("кв.")) {
                        addressDto.setFlat(value.substring(value.indexOf("кв.") + 4));
                    }
                    break;
            }
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
