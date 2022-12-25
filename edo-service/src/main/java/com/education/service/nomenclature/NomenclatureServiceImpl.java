package com.education.service.nomenclature;

import com.education.service.nomenclature.NomenclatureService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.NomenclatureDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
@Log4j2
public class NomenclatureServiceImpl implements NomenclatureService {

    private final String URL = "http://edo-repository/api/repository/nomenclature";
    private RestTemplate restTemplate;

    @Override
    public void save(NomenclatureDto nomenclatureDto) {
        restTemplate.postForObject(URL + "/add", nomenclatureDto, NomenclatureDto.class);
    }

    @Override
    public NomenclatureDto findById(Long id) {
        return restTemplate.getForObject(URL + "/find/" + id, NomenclatureDto.class);
    }

    @Override
    public List<NomenclatureDto> findAllById(String ids) {
        return restTemplate.getForObject(URL+"/allId?id=" + ids, List.class);
    }

    @Override
    public void deleteById(Long id) {
        restTemplate.delete(URL + "/delete/" + id);
    }

    public void moveToArchive(Long id) {
        restTemplate.postForObject(URL + "/move/" + id, null, NomenclatureDto.class);
    }

    @Override
    public NomenclatureDto findByIdNotArchived(Long id) {
        return restTemplate.getForObject(URL + "/find_not_archived/" + id, NomenclatureDto.class);
    }

    @Override
    public List<NomenclatureDto> findAllByIdNotArchived(String ids) {
        return restTemplate.getForObject(URL + "/find_not_archived_List?id=" + ids,List.class);
    }
}
