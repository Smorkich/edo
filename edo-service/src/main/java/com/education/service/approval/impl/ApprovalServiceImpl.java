package com.education.service.approval.impl;

import com.education.service.approval.ApprovalService;
import com.education.service.approvalBlock.ApprovalBlockService;
import com.education.service.member.MemberService;
import com.education.util.URIBuilderUtil;
import com.education.util.Validator;
import lombok.AllArgsConstructor;
import model.dto.ApprovalBlockDto;
import model.dto.ApprovalDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static model.constant.Constant.EDO_REPOSITORY_NAME;

/**
 * Сервис слой для взаимодействия с ApprovalBlockDto
 */
@Service
@AllArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {
    private final RestTemplate restTemplate;
    private final ApprovalBlockService approvalBlockService;
    private final MemberService memberService;
    private final Validator validator;

    /**
     * Отправляет post-запрос в edo-repository для сохранения листа согласования
     */
    @Override
    public ApprovalDto save(ApprovalDto approvalDto) {

        // Проверка на отсутствие индекса у листа согласования
        if (approvalDto.getId() != null) {
            throw new IllegalArgumentException("The approval must be without an id");
        }

        // Валидация листа согласования
        validator.validateApprovalDto(approvalDto);

        // Список, который хранит новые блоки
        List<ApprovalBlockDto> savedApprovalBlocks = new ArrayList<>();

        try {
            // Сохранение блоков согласования
            approvalDto.setParticipantApprovalBlocks(approvalDto.getParticipantApprovalBlocks().stream()
                    .map(approvalBlockDto -> {
                        approvalBlockDto = approvalBlockService.save(approvalBlockDto);
                        savedApprovalBlocks.add(approvalBlockDto);

                        return approvalBlockDto;
                    })
                    .collect(Collectors.toList()));
            approvalDto.setSignatoryApprovalBlocks(approvalDto.getSignatoryApprovalBlocks().stream()
                    .map(approvalBlockDto -> {
                        approvalBlockDto = approvalBlockService.save(approvalBlockDto);
                        savedApprovalBlocks.add(approvalBlockDto);

                        return approvalBlockDto;
                    })
                    .collect(Collectors.toList()));

            // Сохранение инициатора
            approvalDto.setInitiator(memberService.save(approvalDto.getInitiator()));

            // Установка даты создания для листа согласования
            approvalDto.setCreationDate(ZonedDateTime.now());

            String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval").toString();

            return restTemplate.postForObject(uri, approvalDto, ApprovalDto.class);
        } catch (Exception e) {

            // Удаление сохранённых вложенных сущностей
            savedApprovalBlocks.forEach(approvalBlockDto -> approvalBlockService.delete(approvalBlockDto.getId()));
            if (approvalDto.getInitiator().getId() != null) memberService.delete(approvalDto.getInitiator().getId());

            throw e;
        }
    }

    /**
     * Отправляет get-запрос в edo-repository для получения листа согласования по индексу
     */
    @Override
    public ApprovalDto findById(Long id) {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/" + id).toString();

        return restTemplate.getForObject(uri, ApprovalDto.class);
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех листов согласования
     */
    @Override
    public Collection<ApprovalDto> findAll() {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/all").toString();

        return restTemplate.getForObject(uri, List.class);
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех листов согласования с полученными индексами
     */
    @Override
    public Collection<ApprovalDto> findAllById(Iterable<Long> ids) {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/all/" + ids).toString();

        return restTemplate.getForObject(uri, List.class);
    }

    /**
     * Отправляет delete-запрос в edo-repository для удаления листа согласования по индексу
     */
    @Override
    public void delete(Long id) {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/" + id).toString();

        restTemplate.delete(uri);
    }

    /**
     * Отправляет patch-запрос в edo-repository для добавления даты архивации листу согласования
     */
    @Override
    public ApprovalDto moveToArchive(Long id) {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/move/" + id).toString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.exchange(uri, HttpMethod.PATCH, new HttpEntity<>(headers), ApprovalDto.class).getBody();
    }

    /**
     * Отправляет get-запрос в edo-repository для получения листа согласования без даты архивации по индексу
     */
    @Override
    public ApprovalDto findByIdNotArchived(Long id) {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/findByIdNotArchived/" + id).toString();

        return restTemplate.getForObject(uri, ApprovalDto.class);
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех листов согласования без даты архивации
     */
    @Override
    public Collection<ApprovalDto> findAllNotArchived() {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/findAllNotArchived").toString();

        return restTemplate.getForObject(uri, List.class);
    }

    /**
     * Отправляет get-запрос в edo-repository для получения всех листов согласования без даты архивации с полученными индексами
     */
    @Override
    public Collection<ApprovalDto> findByIdInAndArchivedDateNull(Iterable<Long> ids) {
        String uri = URIBuilderUtil.buildURI(EDO_REPOSITORY_NAME, "/api/repository/approval/archive/all/findByIdInAndArchivedDateNull/" + ids).toString();

        return restTemplate.getForObject(uri, List.class);
    }
}
