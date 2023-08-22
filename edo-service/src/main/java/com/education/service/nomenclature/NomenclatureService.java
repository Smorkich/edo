package com.education.service.nomenclature;


import model.dto.AppealDto;
import model.dto.NomenclatureDto;

import java.util.List;

/**
 * Interface for service from edo-service module, calling another service from edo-repository module.
 */
public interface NomenclatureService {

    /**
     * Method saves new entity in DB by accepting json-body object
     */
    void save(NomenclatureDto nomenclatureDto);

    /**
     * Method searches for an entity of Nomenclature
     */
    NomenclatureDto findById(Long id);

    /**
     * Method searches for set of entities of Nomenclature by their ids: "?id = 1,2,3,4,5,6... "
     */
    List<NomenclatureDto> findAllById(String ids);


    /**
     * Delete entity of Nomenclature by its id
     */
    void deleteById(Long id);

    /**
     * the Method fills in the field with the value and set date
     */
    void moveToArchive(Long id);

    /**
     * Method searches for an entity of Nomenclature that archiveDate fild is null
     */
    NomenclatureDto findByIdNotArchived(Long id);

    /**
     * Method searches for set of entities of Nomenclature that archiveDate filds are null
     */
    List<NomenclatureDto> findAllByIdNotArchived(String ids);

    List findByIndex(String index);

    /**
     * Интерфейс для метода, который генерирует number с использованием индекса, года и номера и назначает его Appeal'у
     * <p>Вспомогательный метод к методу "save"
     *
     * @param appealDto обращение к которому мы хотим прикрепить генерируемый номер в поле number
     */
    void generateAppealNumber(AppealDto appealDto);
}
