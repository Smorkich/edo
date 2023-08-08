package com.education.service.nomenclature;

import model.dto.NomenclatureDto;

import java.util.List;

public interface NomenclatureService {

    List<NomenclatureDto> findByIndex(String index);
}
