package com.education.serivce.nomenclature.impl;

import com.education.entity.Nomenclature;
import com.education.repository.NomenclatureRepository;
import com.education.serivce.nomenclature.NomenclatureService;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
