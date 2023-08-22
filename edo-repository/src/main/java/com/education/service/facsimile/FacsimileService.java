package com.education.service.facsimile;

import com.education.entity.Facsimile;
import org.springframework.data.repository.query.Param;

public interface FacsimileService {

    Facsimile save(Facsimile facsimile);
    Facsimile findById(Long id);
    Facsimile findFacsimileByEmployeeId(Long employeeId);
}
