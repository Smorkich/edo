package com.education.service.nomenclature;

import model.dto.NomenclatureDto;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

public interface NomenclatureService {

    List<NomenclatureDto> findByIndex(String index) throws URISyntaxException, MalformedURLException;
}
