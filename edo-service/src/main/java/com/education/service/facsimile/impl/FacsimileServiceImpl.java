package com.education.service.facsimile.impl;

import com.education.feign.FacsimileFeignClient;
import com.education.service.facsimile.FacsimileService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.FacsimileDto;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class FacsimileServiceImpl implements FacsimileService {

    private final FacsimileFeignClient facsimileFeignClient;

    @Override
    public FacsimileDto save(FacsimileDto facsimileDto) {
        return facsimileFeignClient.save(facsimileDto);
    }
}
