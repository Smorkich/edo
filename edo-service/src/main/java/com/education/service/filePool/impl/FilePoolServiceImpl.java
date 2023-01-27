package com.education.service.filePool.impl;

import com.education.service.filePool.FilePoolService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import model.dto.FilePoolDto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

/**
 * @author Nadezhda Pupina
 * Сервис реализует методы jpa repository и обычные методы
 */
@Service
@Log4j2
@AllArgsConstructor
public class FilePoolServiceImpl implements FilePoolService {

    static final String URL = "http://edo-repository/api/repository/filePool";
    private final RestTemplate restTemplate;

    @Override
    public void save(FilePoolDto filePoolDto) {
        restTemplate.postForObject(URL, filePoolDto, FilePoolDto.class);
    }

    @Override
    public void delete(Long id) {
        restTemplate.delete(URL + "/" + id, FilePoolDto.class);
    }

    @Override
    public String findById(Long id) {
        return restTemplate.getForObject(URL + "/" + id, String.class);
    }

    public String findAll() {
        return restTemplate.getForObject(URL + "/all", String.class);
    }

    @Override
    public Collection<FilePoolDto> findAllById(String ids) {
        return restTemplate.getForObject(URL + "/all/" + ids, List.class);
    }

    @Override
    public void moveToArchive(Long id) {
        restTemplate.postForObject(URL + "/" + id, null, FilePoolDto.class);
    }

    @Override
    public FilePoolDto findByIdNotArchived(Long id) {
        return restTemplate.getForObject(URL + "/notArchived/" + id,FilePoolDto.class);
    }

    @Override
    public Collection<FilePoolDto> findAllByIdNotArchived(String ids) {
        return restTemplate.getForObject(URL + "/notArchivedAll/" + ids, List.class);
    }

}
