package com.education.service.facsimile;

import com.education.entity.Facsimile;

public interface FacsimileService {

    Facsimile save(Facsimile facsimile);
    Facsimile findById(Long id);

}
