package com.education.service.facsimile.impl;

import com.education.entity.Facsimile;
import com.education.repository.facsimile.FacsimileRepository;
import com.education.service.facsimile.FacsimileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class FacsimileServiceImpl implements FacsimileService {

    private FacsimileRepository facsimileRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Facsimile save(Facsimile facsimile) {
        return facsimileRepository.save(facsimile);
    }

    @Override
    @Transactional(readOnly = true)
    public Facsimile findById(Long id) {
        return facsimileRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Facsimile findFacsimileByEmployeeId(Long employeeId) {
        return facsimileRepository.findFacsimileByEmployeeId(employeeId).orElse(null);
    }
}
