package com.education.service.nomenclature.impl;

import com.education.entity.Nomenclature;
import com.education.repository.nomenclature.NomenclatureRepository;
import com.education.service.nomenclature.NomenclatureService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class NomenclatureServiceImpl implements NomenclatureService {

    NomenclatureRepository nomenclatureRepository;

    @Override
    @Transactional(readOnly = true)
    public Nomenclature findById(Long id) {
        return nomenclatureRepository.findById(id).orElse(null);
    }
}
