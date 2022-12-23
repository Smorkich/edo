package com.education.service.nomenclature.impl;

import com.education.entity.Nomenclature;
import com.education.repository.nomenclature.NomenclatureRepository;
import com.education.service.nomenclature.NomenclatureService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class NomenclatureServiceImpl implements NomenclatureService {

    private NomenclatureRepository repository;

    @Transactional(rollbackFor = Exception.class)
    public void save(Nomenclature nomenclature) {
        repository.save(nomenclature);
    }

    @Transactional(rollbackFor = Exception.class)
    public void moveToArchive(Long id) {
        repository.moveToArchive(ZonedDateTime.now(), id);
    }

    public Optional<Nomenclature> findById(Long id) {
        return repository.findById(id);
    }


    public List<Nomenclature> findAllById(Collection<Long> nomenclature) {
        List<Nomenclature> allById = repository.findAllById(nomenclature);
        return allById;
    }
    
    @Override
    public Optional<Nomenclature> findByIdNotArchived(Long id) {
        return repository.findByIdNotArchived(id);
    }

    @Override
    public List<Nomenclature> findAllByIdNotArchived(Collection<Long> idList) {
        return repository.findAllByIdNotArchived(idList);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
