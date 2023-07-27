package com.education.service.nomenclature.impl;


import com.education.feign.NomenclatureFeignClient;
import com.education.service.nomenclature.NomenclatureService;
import com.education.util.KeySwitcherUtil;
import com.education.util.URIBuilderUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.constant.Constant;
import model.dto.NomenclatureDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.education.util.URIBuilderUtil.buildURI;
import static model.constant.Constant.*;

@AllArgsConstructor
@Service
@Log4j2
public class NomenclatureServiceImpl implements NomenclatureService {

    private NomenclatureFeignClient nomenclatureFeignClient;
    private final String NOMEN_REPO_URL = "api/repository/nomenclature/find";


    @Override
    public void save(NomenclatureDto nomenclatureDto) {
        nomenclatureFeignClient.saveDefaultEntity(nomenclatureDto);
    }

    @Override
    public NomenclatureDto findById(Long id) {
        return nomenclatureFeignClient.findByID(id);
    }

    @Override
    public List<NomenclatureDto> findAllById(String ids) {
        return nomenclatureFeignClient.findAllByIdController(ids);
    }

    @Override
    public void deleteById(Long id) {
        nomenclatureFeignClient.deleteById(id);
    }

    @Override
    public void moveToArchive(Long id) {
        nomenclatureFeignClient.move(id);
    }

    @Override
    public NomenclatureDto findByIdNotArchived(Long id) {
        return nomenclatureFeignClient.findByIdNotArchivedController(id);
    }

    @Override
    public List<NomenclatureDto> findAllByIdNotArchived(String ids) {
        return nomenclatureFeignClient.findAllByIdNotArchivedController(ids);
    }

    @Override
    public List<NomenclatureDto> findByIndex(String index) {
        return nomenclatureFeignClient.findByIndex(index);
    }
}
